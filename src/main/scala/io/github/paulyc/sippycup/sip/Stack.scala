/**
  * Stack.scala
  *
  * Copyright (C) 2018 Paul Ciarlo <paul.ciarlo@gmail.com>
  *
  * Permission is hereby granted, free of charge, to any person obtaining a copy
  * of this software and associated documentation files (the "Software"), to deal
  * in the Software without restriction, including without limitation the rights
  * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  * copies of the Software, and to permit persons to whom the Software is
  * furnished to do so, subject to the following conditions:
  *
  * The above copyright notice and this permission notice shall be included in all
  * copies or substantial portions of the Software.
  *
  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
  * SOFTWARE.
  */

package io.github.paulyc.sippycup.sip

import java.util.Properties

import akka.actor.{Actor, ActorLogging, Props}
import javax.sip.SipFactory

object Stack {
  def props(config: ProviderConfig) = Props(new Stack(config))
}

class Stack(config: ProviderConfig) extends Actor with ActorLogging {
  case class Start()
  case class Stop()

  private val props = new Properties()
  props.setProperty("javax.sip.STACK_NAME", "sippycup")
  props.setProperty("javax.sip.USE_ROUTER_FOR_ALL_URIS", "false")
  props.setProperty("gov.nist.javax.sip.DEBUG_LOG", "debuglog.txt")
  props.setProperty("gov.nist.javax.sip.SERVER_LOG", "serverlog.txt")
  props.setProperty("javax.sip.FORKABLE_EVENTS", "foo")
  props.setProperty("javax.sip.AUTOMATIC_DIALOG_SUPPORT", if (true) "on" else "off")
  // 0 => none
  // 16 => debug
  // 32 => debug + traces (slow)
  props.setProperty("gov.nist.javax.sip.TRACE_LEVEL", Integer.toString(32))

  private val factory = SipFactory.getInstance()
  factory.setPathName("io.github.paulyc")

  private val sipStack = factory.createSipStack(props)
  private val headerFactory = factory.createHeaderFactory()
  private val addressFactory = factory.createAddressFactory()
  private val messageFactory = factory.createMessageFactory()

  private val dispatcher = context.actorOf(Dispatcher.props())

  override def preStart(): Unit = {
    sipStack.start()

    val provider = new NetworkProvider(dispatcher,
      sipStack.createSipProvider(
        sipStack.createListeningPoint(
          config.host,
          config.port,
          config.protocol
        )
      )
    )
    // ? dispatcher ! self
  }

  def receive : Receive = hello

  def hello : Receive = {
    case _ => log.info("Stack got a surprise message")
  }

  def initialized : Receive = {
    case Start() =>
      log.info("Stack starting")
      context.become(running)
    case _ =>
      log.warning("Stack not started")
  }

  def running : Receive = {
    case Stop() =>
      log.info("Stack stopping")
      sipStack.stop()
      context.become(dead)
    case _ =>
      log.warning("Stack running got unknown message")
  }

  def dead : Receive = {
    case _ =>
      log.warning("Stack dead")
  }
}
