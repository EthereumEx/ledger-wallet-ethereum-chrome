package co.ledger.wallet.core.wallet.ethereum

import co.ledger.wallet.core.concurrent.AsyncCursor
import co.ledger.wallet.core.device.utils.EventEmitter

import scala.concurrent.Future

/**
  *
  * Wallet
  * ledger-wallet-ethereum-chrome
  *
  * Created by Pierre Pollastri on 13/06/2016.
  *
  * The MIT License (MIT)
  *
  * Copyright (c) 2016 Ledger
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
  *
  */
trait Wallet {
  def name: String
  def bip44CoinType: String
  def coinPathPrefix: String
  def account(index: Int): Future[Account]
  def accounts(): Future[Array[Account]]
  def balance(): Future[Ether]
  def synchronize(): Future[Unit]
  def isSynchronizing(): Future[Boolean]
  def mostRecentBlock(): Future[Block]
  def pushTransaction(transaction: Array[Byte]): Future[Unit]
  def operations(from: Int, batchSize: Int = Wallet.DefaultOperationsBatchSize): Future[AsyncCursor[Operation]]
  def eventEmitter: EventEmitter
  def estimatedGasPrice(): Future[Ether]
  def stop(): Unit
}

object Wallet {
  val DefaultOperationsBatchSize = 20

  case class WalletNotSetupException() extends Exception("The wallet is currently empty")

  case class NewOperationEvent(account: Account, operation: Operation)
  case class StartSynchronizationEvent()
  case class StopSynchronizationEvent()
  case class GasPriceChanged(price: Ether)
}