package com.rover.sudoku

import scala.collection.mutable.ArrayBuffer
import org.apache.log4j._

/**
 * Created by Nan on 15/1/23.
 */
class ChessBoard {

  val LOG = org.apache.log4j.Logger.getLogger("ChessBoard")

  private val board = new ArrayBuffer[Checker](Constant.BOARD_SIZE * Constant.BOARD_SIZE)
  for (i <- 0 until board.length) {
    board(i) = new Checker(Constant.NONE_VALUE, i)
  }

  def initialize(values : Array[(Int, Int)]) = {
    values.map(it => {
      if(it._1 < board.length) {
        board(it._1) = new Checker(it._2, it._1)
      }
    })
  }

  def update() = {
    var updatedFlag = false
    board.filter(it => !it.hasValue).foreach(it => {
      val row = getRow(it.getRowIndex)
      updatedFlag = updatedFlag || it.updateInGroup(row)

      if (!it.hasValue) {
        val col = getColumn(it.getColumnIndex)
        updatedFlag = updatedFlag || it.updateInGroup(col)
      }

      if (!it.hasValue) {
        val rect = getRect(it.getRectIndex)
        updatedFlag = updatedFlag || it.updateInGroup(rect)
      }
    })

    updatedFlag
  }

  def process() = {
    LOG.info("Original board:")
    print

    LOG.info("Start process...")

    while(update) { print }
  }

  private def print() = {
    board.groupBy(it => it.getRowIndex).map(it => {
      it._2.mkString(",")
    }).foreach(LOG.info)
    LOG.info("\n")
  }

  private def getRow(rowIndex: Int) = {
    val startIndex = rowIndex * Constant.BOARD_SIZE
    board.slice(startIndex, startIndex + Constant.BOARD_SIZE)
  }

  private def getColumn(columnIndex : Int) = {
    board.filter(it => it.getColumnIndex == columnIndex)
  }

  private def getRect(rectIndex: Int) = {
    board.filter(it => it.getRectIndex == rectIndex)
  }

}
