/**
  * Dispatcher.scala
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

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import javax.sip._
import javax.sip.message.{Request, Response}

object Dispatcher {
  final case class OnRequest(request: Request, serverTransaction: javax.sip.ServerTransaction, dialog: Dialog)
  final case class OnResponse(response: Response, clientTransaction: javax.sip.ClientTransaction, dialog: Dialog)
  final case class OnTimeout(timeout: Timeout, transaction: Either[javax.sip.ServerTransaction, javax.sip.ClientTransaction])
  final case class OnIOException(host: String, port: Int, transport: String)
  final case class OnTransactionTerminated(transaction: Either[javax.sip.ServerTransaction, javax.sip.ClientTransaction])
  final case class OnDialogTerminated(dialog: Dialog)

  def props() = Props(new Dispatcher())
}

/**
  * Handles SIP messages going in and out of the network
  */
class Dispatcher extends Actor with ActorLogging {
  import Dispatcher._

  case class StartTransaction(transaction: Transaction)

  def receive : Receive = {
    case OnRequest(_, _, _)         =>
    case OnResponse(_, _, _)        =>
    case OnTimeout(_, _)            =>
    case OnIOException(_, _, _)     =>
    case OnTransactionTerminated(_) =>
    case OnDialogTerminated(_)      =>

    case StartTransaction(_)  =>
    case _ =>
  }


}
