/**
  * Parser.scala
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

import io.github.paulyc.sippycup.sip.SipRequest._

import scala.util.matching.Regex

// probably useless now.... alrady implemented in jsip
/*
object Parser {
  val sipMessage : Regex = raw"^(.+?) (.+?) (SIP/.+?)\r\n((.+?):\w*(.+?)\w*\r\n)*\r\n(.*)$$"r("method", "requestUri", "sipVersion", "headers", "body")
  val headerSection : Regex = raw"(.+?:\w*.+?\w*\r\n)*"r("headerLines")
  val headerLine : Regex = raw"(.+?):\w*(.+?)\w*"r("key", "value")

  def parseHeaders(headers: String) = Headers(
    headers.split(raw"\r\n").map {
      case headerLine(key, value) => Some(Header(key, value))
      case _ => None
    } flatten
  )

  def parse(msg: String) : Option[Product] = {
    msg match {
      case sipMessage(method, requestUri, sipVersion, headerString, body) =>
        val (headers, body) = (parseHeaders(headerString), Body(body))
        method.toUpperCase match {
          case "INVITE"     => Some(Invite(headers, body))
          case "ACK"        => Some(Ack(headers, body))
          case "BYE"        => Some(Bye(headers, body))
          case "CANCEL"     => Some(Cancel(headers, body))
          case "OPTIONS"    => Some(Options(headers, body))
          case "REGISTER"   => Some(Register(headers, body))
          case "PRACK"      => Some(Prack(headers, body))
          case "SUBSCRIBE"  => Some(Subscribe(headers, body))
          case "NOTIFY"     => Some(Notify(headers, body))
          case "PUBLISH"    => Some(Publish(headers, body))
          case "INFO"       => Some(Info(headers, body))
          case "REFER"      => Some(Refer(headers, body))
          case "MESSAGE"    => Some(Message(headers, body))
          case "UPDATE"     => Some(Update(headers, body))
          case _            => None
        }
      case _ => None
    }
  }
}*/
