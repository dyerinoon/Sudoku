package com.rover.sudoku

import org.apache.log4j.Logger

import scala.collection.mutable.ArrayBuffer

/**
 * Created by dyer on 28/01/15.
 */

abstract class Action {
  def execute(): Boolean
  def undo(): Boolean
}


class UpdateCheckerOneRuleAction(checker: Checker, board: ChessBoard, rule: Rule) extends Action {

  private val logger = Logger.getLogger(getClass.getName)

  private val removedCandidates = if (!checker.hasValue) {
    rule.getHasValueCheckerGroup(checker, board).map(it => it.getValue)
  } else { new ArrayBuffer[Int]()  }

  override def execute(): Boolean = {

    if (!checker.hasValue) {
      checker.candidateValue --= removedCandidates

      /*

      */
    }

    !removedCandidates.isEmpty
  }

  override def undo(): Boolean = {
    checker.candidateValue ++= removedCandidates
    checker.candidateValue.distinct
    /*if (valueChanged) {
      logger.info("Undo update checker value at: (" + checker.getRowIndex + ", " +
        checker.getColumnIndex + "), undo value is: " + checker.value)

      checker.candidateValue += checker.value
      checker.value = Constant.NONE_VALUE
    }*/

    !removedCandidates.isEmpty
  }
}

class UpdateCheckerRulesAction(checker: Checker, board: ChessBoard, rules: Array[Rule]) extends Action {
  private val actionHistory = new ArrayBuffer[Action]

  private val logger = Logger.getLogger(getClass.getName)
  private var valueChenaged = false

  override def execute(): Boolean = {
    rules.foreach (rule => {
      if(!checker.hasValue) {
        val action = new UpdateCheckerOneRuleAction(checker, board, rule)
        actionHistory += action
        action.execute()
      }
    })

    valueChenaged = if (checker.candidateValue.size == 1) {
      checker.value = checker.candidateValue(0)
      checker.candidateValue.clear

      logger.info("update checker value at: (" + checker.getRowIndex + ", " +
        checker.getColumnIndex + "), new value is: " + checker.value)

      true
    }
    else {
      false
    }

    valueChenaged
  }

  override def undo(): Boolean = {
    actionHistory.reverse.map(it => it.undo())
    if (valueChenaged) {
      checker.value = Constant.NONE_VALUE
    }

    valueChenaged
  }
}

class UpdateBoardAction(board: ChessBoard) extends Action {
  private val logger = Logger.getLogger(getClass.getName)
  private val actionHistory = new ArrayBuffer[Action]

  override def execute(): Boolean = {

    val updatedFlag = board.getAll.filter(it => !it.hasValue).map(it => {
      val rules = RuleFactory.getRules()
      val action = new UpdateCheckerRulesAction(it, board, rules)
      actionHistory += action
      action.execute()
    })

    logger.info("After updating the board is:" + board.toString)

    updatedFlag.contains(true)
  }

  override def undo(): Boolean = {
    val result = actionHistory.reverse.map(it => it.undo()).reduce(_ || _)
    logger.info("After undo updating the board is:" + board.toString)

    result
  }
}

class ForceSetValueAction(val newValue: Int, checker: Checker) extends Action {
  private val logger = Logger.getLogger(getClass.getName)

  private val candidates = new ArrayBuffer[Int]

  override def execute(): Boolean = {
    logger.info("Force set value at (" + checker.getRowIndex +
      ", " + checker.getColumnIndex + "), new value is: " + newValue)
    checker.value = newValue
    candidates ++= checker.candidateValue
    checker.candidateValue.clear

    true
  }

  override def undo(): Boolean = {
    logger.info("Undo force set value at (" + checker.getRowIndex +
      ", " + checker.getColumnIndex + "), undo value is: " + newValue)

    checker.value = Constant.NONE_VALUE
    checker.candidateValue.clear
    checker.candidateValue ++= this.candidates

    true
  }
}

class AttemptUpdateBoardAction(board: ChessBoard, rules : Array[Rule]) extends Action {

  private val logger = Logger.getLogger(getClass.getName)

  override def execute(): Boolean = {
    val checkers = board.getAll.filter(it => !it.hasValue)
    val first = checkers(0)

    val candidates = new ArrayBuffer[Int]()
    candidates ++= first.candidateValue
    candidates.foreach (it => {
      val forceSetValueAction = new ForceSetValueAction(it, first)
      forceSetValueAction.execute()

      val updateBoardAction = new UpdateBoardAction(board)
      updateBoardAction.execute()
      if (board.isConflict) {
        forceSetValueAction.undo
        updateBoardAction.undo
      } else if (board.isAllValueBeSet()) {
        return true
      } else {
        val newAttempt = new AttemptUpdateBoardAction(board, rules)
        if (newAttempt.execute()) {
          return true
        }
      }
    })

    false
  }


  override def undo(): Boolean = ???
}