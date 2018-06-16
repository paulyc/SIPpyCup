/**
  * Application.scala
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

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}

class Application(val args: Array[String]) extends Thread {
  val system = ActorSystem("sippycup-system")

  override def run(): Unit = {
    try {
      val supervisor = system.actorOf(SippySupervisor.props(this), "sippy-supervisor")

    } finally {
      system.terminate()
    }
  }

  // orderly polite shutdown of the system
  def handleShutdown(): Unit = {

  }

  // immediately terminate the system but at least wait for it
  // to finish terminating before exiting the process
  def handleTerminate(): Unit = {
    system.terminate()
  }

  def handleKill(): Unit = {
    system.terminate()
  }

}
