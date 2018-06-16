/**
  * Console.scala
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

package io.github.paulyc.sippycup

import java.nio.channels.ClosedByInterruptException
import java.util.concurrent.atomic.AtomicBoolean

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import io.github.paulyc.sippycup.Console.{ConsoleMessage, Die}

import scala.io.StdIn

object Console {
  def props(controller: ActorRef) = Props(new Console(controller))

  final case class ConsoleMessage(msg: String)
  final case class Die()
}

class Console(controller: ActorRef) extends Actor with ActorLogging {
  private val running = new AtomicBoolean(true)
  private val readThread = new java.lang.Thread(() => {
    while (running.get()) {
      try {
        self ! StdIn.readLine("SIPpyCup> ")
      } catch {
        case ex : ClosedByInterruptException =>
        case _ : Throwable =>
      }
    }
  })

  readThread.start()

  def receive : Receive = {
    case m: ConsoleMessage => controller.forward(m)
    case _: Die =>
      running.compareAndSet(true, false)
      readThread.interrupt()
      readThread.join()
  }

}
