package com.rover.sudoku

import scala.collection.mutable.ArrayBuffer

/**
 * Created by dyer on 28/01/15.
 */

abstract class Action {
  def execute() : Boolean
  def undo() : Boolean
  def isConflict() : Boolean
}


class UpdateCheckerAction(checker: Checker, group: ArrayBuffer[Checker]) extends Action {

  private val removedCandidates = new ArrayBuffer[Int]
  private var updatedValue = false

  override def execute() : Boolean = {
    if(!checker.hasValue) {
      val needRemove = group.filter(it => it.hasValue).map(it => it.getValue)
      checker.candidateValue --= needRemove
      removedCandidates ++= needRemove

      if (checker.candidateValue.size == 1) {
        checker.value = checker.candidateValue(0)
        checker.candidateValue.clear

        updatedValue = true
      }
    }

    updatedValue
  }

  override def undo() : Boolean = {
    checker.candidateValue ++= removedCandidates
    if (updatedValue) {
      checker.candidateValue += checker.value
      checker.value = Constant.NONE_VALUE

      updatedValue = false
    }

    false
  }

  override def isConflict() : Boolean = {
    if(!checker.hasValue) {
      val needRemove = group.filter(it => it.hasValue).map(it => it.getValue)
      val diff = checker.candidateValue.diff(needRemove)

      return diff.isEmpty
    }

    return false
  }
}

