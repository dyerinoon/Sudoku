package com.rover.sudoku

import org.scalatest.FlatSpec

/**
 * Created by dyer on 29/01/15.
 */
class ChessboardFileSpec extends FlatSpec {

  "A Chessboard" can "be read from a text file" in {
    val chessBoard = ChessboardFile.read("data/chessboard.txt")
    assert(chessBoard.getRow(0)(5).getValue == 4)

  }
}


