package com.rover.sudoku

import org.scalatest.FlatSpec

/**
 * Created by dyer on 30/01/15.
 */
class UpdateBoardActionSpec extends  FlatSpec {

  "A Update Board Action" can "remove all conflicts in the board" in {
    val chessboard = ChessboardFile.read("data/chessboard.txt")
    val updateBoard = new UpdateBoardAction(chessboard)
    while (updateBoard.execute()) {
    }

    if (!chessboard.isAllValueBeSet) {
      val attemptAction = new AttemptUpdateBoardAction(chessboard, RuleFactory.getRules)
      attemptAction.execute
    }
  }

}
