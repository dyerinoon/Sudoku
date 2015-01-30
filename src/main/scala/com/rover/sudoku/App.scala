package com.rover.sudoku

import scala.collection.mutable.ArrayBuffer

/**
 * Created by dyer on 29/01/15.
 */
object App {

  def main(args: Array[String]): Unit = {

    val chessboard = ChessboardFile.read("data/chessboard.txt")
    val updateBoard = new UpdateBoardAction(chessboard)
    while (updateBoard.execute()) {

    }
  }

}
