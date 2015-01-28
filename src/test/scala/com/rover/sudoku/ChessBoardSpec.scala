package com.rover.sudoku

import org.scalatest.FlatSpec

import scala.collection.mutable.ArrayBuffer

/**
 * Created by Nan on 15/1/24.
 */
class ChessBoardSpec extends FlatSpec {

  "A ChessBoard" can "update checker value without conflict" in {
    val chessBoard = new ChessBoard(Array((2, 7), (6, 2), (13, 3), (18, 2), (21, 6), (22, 9), (23, 5), (26, 7), (29, 5), (33, 7), (37, 9), (38, 4), (40, 8), (42, 5), (43, 2), (47, 8), (51, 3), (54, 4), (57, 9), (58, 1), (59, 7), (62, 6), (67, 5), (74, 3), (78, 1)))
    
    chessBoard.process()



  }
}
