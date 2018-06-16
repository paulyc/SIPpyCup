/**
  * NetworkProvider.scala
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

import akka.actor.{Actor, ActorRef}
import io.github.paulyc.sippycup.sip.Dispatcher._
import javax.sip._

class NetworkProvider(dispatcher: ActorRef, provider: SipProvider) extends SipListener {
  provider.addSipListener(this)

  override def processRequest(requestEvent: RequestEvent): Unit = {
    dispatcher ! OnRequest(
      requestEvent.getRequest,
      requestEvent.getServerTransaction,
      requestEvent.getDialog
    )
  }

  override def processResponse(responseEvent: ResponseEvent): Unit = {
    dispatcher ! OnResponse(
      responseEvent.getResponse,
      responseEvent.getClientTransaction,
      responseEvent.getDialog
    )
  }

  override def processTimeout(timeoutEvent: TimeoutEvent): Unit = {
    dispatcher ! OnTimeout(
      timeoutEvent.getTimeout,
      if (timeoutEvent.isServerTransaction)
        Left(timeoutEvent.getServerTransaction)
      else
        Right(timeoutEvent.getClientTransaction)
    )
  }

  override def processIOException(ioExceptionEvent: IOExceptionEvent): Unit = {
    dispatcher ! OnIOException(
      ioExceptionEvent.getHost,
      ioExceptionEvent.getPort,
      ioExceptionEvent.getTransport
    )
  }

  override def processTransactionTerminated(transactionTerminatedEvent: TransactionTerminatedEvent): Unit = {
    dispatcher ! OnTransactionTerminated(
      if (transactionTerminatedEvent.isServerTransaction)
        Left(transactionTerminatedEvent.getServerTransaction)
      else
        Right(transactionTerminatedEvent.getClientTransaction)
    )
  }

  override def processDialogTerminated(dialogTerminatedEvent: DialogTerminatedEvent): Unit = {
    dispatcher ! OnDialogTerminated(
      dialogTerminatedEvent.getDialog
    )
  }
}
