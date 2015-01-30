package com.rover.sudoku

import scala.collection.mutable.ArrayBuffer

/**
 * Created by dyer on 30/01/15.
 */
abstract class Rule() {
  def isConflict(value : Int, checker: Checker, chessBoard: ChessBoard) = {
    getHasValueCheckerGroup(checker, chessBoard).contains(value)
  }

  def getHasValueCheckerGroup(checker: Checker, chessBoard: ChessBoard) = {
    getCheckerGroup(checker, chessBoard).filter(it => it.hasValue)
  }

  def getCheckerGroup(checker: Checker, chessBoard: ChessBoard) : ArrayBuffer[Checker]
}

class RowRule() extends Rule {

  override def getCheckerGroup(checker: Checker, chessBoard: ChessBoard) = {
    chessBoard.getRow(checker.getRowIndex)
  }
}

class ColumnRule() extends Rule {

  override def getCheckerGroup(checker: Checker, chessBoard: ChessBoard) = {
    chessBoard.getColumn(checker.getColumnIndex)
  }
}

class RectRule() extends Rule {

  override def getCheckerGroup(checker: Checker, chessBoard: ChessBoard) = {
    chessBoard.getRect(checker.getRectIndex)
  }
}

object RuleFactory {
  def getRules() = {
    Array(new RowRule(), new ColumnRule(), new RectRule())
  }

  def isConflict(value: Int, checker: Checker, chessBoard: ChessBoard) = {
    getRules.map(it => {
      it.isConflict(value, checker, chessBoard)
    }).contains(true)
  }
}
