/**
  * sip/package.scala
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

package object sip {

  case class RawMessage(msg: String)

  case class Header(key: String, value: String)
  case class Body(value: String)
  case class Headers(headers: Seq[Header])

  sealed trait SipRequest
  sealed trait SipResponse

  object SipRequest {
    case class Invite(headers: Headers, body: Body) extends SipRequest
    case class Ack(headers: Headers, body: Body) extends SipRequest
    case class Bye(headers: Headers, body: Body) extends SipRequest
    case class Cancel(headers: Headers, body: Body) extends SipRequest
    case class Options(headers: Headers, body: Body) extends SipRequest
    case class Register(headers: Headers, body: Body) extends SipRequest
    case class Prack(headers: Headers, body: Body) extends SipRequest
    case class Subscribe(headers: Headers, body: Body) extends SipRequest
    case class Notify(headers: Headers, body: Body) extends SipRequest
    case class Publish(headers: Headers, body: Body) extends SipRequest
    case class Info(headers: Headers, body: Body) extends SipRequest
    case class Refer(headers: Headers, body: Body) extends SipRequest
    case class Message(headers: Headers, body: Body) extends SipRequest
    case class Update(headers: Headers, body: Body) extends SipRequest
  }

  object SipResponse {

  }

  trait EndpointConfig {
    def host: String
    def port: Int
    def protocol: String
  }

  trait ProviderConfig extends EndpointConfig

  trait TrunkConfig {
    def name: String
    def sid: String
    def terminationUri: String
  }

}
