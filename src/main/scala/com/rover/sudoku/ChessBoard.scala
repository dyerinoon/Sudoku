package com.rover.sudoku

import org.apache.log4j.Logger

import scala.collection.mutable.ArrayBuffer

/**
 * Created by Nan on 15/1/23.
 */
class ChessBoard {

  private val logger = Logger.getLogger(getClass.getName)

  private val board = new ArrayBuffer[Checker](Constant.BOARD_SIZE * Constant.BOARD_SIZE)
  for (i <- 0 until board.length) {
    board(i) = new Checker(Constant.NONE_VALUE, i)
  }

  def initialize(values: Array[(Int, Int)]) = {
    values.map(it => {
      if (it._1 < board.length) {
        board(it._1) = new Checker(it._2, it._1)
      }
    })
  }

  def initialize(values: Array[Int]) = {
    logger.info("initialize the chessboard, input values count = " + values.size)
    board.clear
    values.foreach(it => {
      board += new Checker(it, board.size)
    })
  }

  def copyFrom = {
    val copy = new ChessBoard
    for (i <- 0 until board.length) {
      board(i) = board(i).copyFrom()
    }

    copy
  }

  def getAll = { board }
  def isAllValueBeSet() = {
    board.filter(it => !it.hasValue).isEmpty
  }
  def isConflict = {
    board.map(it => it.candidateValue.isEmpty && !it.hasValue).contains(true)
  }

  def getRow(rowIndex: Int) = {
    val startIndex = rowIndex * Constant.BOARD_SIZE
    board.slice(startIndex, startIndex + Constant.BOARD_SIZE)
  }

  def getColumn(columnIndex: Int) = {
    board.filter(it => it.getColumnIndex == columnIndex)
  }

  def getRect(rectIndex: Int) = {
    board.filter(it => it.getRectIndex == rectIndex)
  }

  override def toString = {
    val sb = new StringBuilder
    sb.append("\n")
    for(i <-0 until Constant.BOARD_SIZE) {
      sb.append(getRow(i).mkString(",")).append("\n")
    }

    sb.toString
  }
}
