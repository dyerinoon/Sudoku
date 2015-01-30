package com.rover.sudoku

import org.apache.log4j.Logger

import scala.io.Source._

/**
 * Created by dyer on 29/01/15.
 */
object ChessboardFile {

  private val logger = Logger.getLogger(getClass.getName)

  /*
  000004018
  000000060
  098075000
  100300000
  050000020
  000002006
  000410630
  020000000
  860700000
  */
  def read(filename: String) = {
    val chessBoard = new ChessBoard()
    val values = fromFile(filename).getLines.mkString(",").split(",").map(_.toInt)

    chessBoard.initialize(values)
    logger.info("Read board: " + chessBoard.toString)

    chessBoard
  }
}
