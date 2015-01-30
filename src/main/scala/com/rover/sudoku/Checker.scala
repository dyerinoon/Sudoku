package com.rover.sudoku

import scala.collection.mutable.ArrayBuffer
import org.apache.log4j.Logger

/**
 * Created by Dong Yan on 15/1/23.
 */
class Checker(var value: Int, val index: Int) {

  private val logger = Logger.getLogger(getClass.getName)

  val candidateValue = ArrayBuffer(1, 2, 3, 4, 5, 6, 7, 8, 9)
  if (value != Constant.NONE_VALUE) {
    candidateValue.clear
  }

  def this(value: Int, index: Int, candidates : ArrayBuffer[Int]) = {
    this(value, index)

    this.candidateValue.clear()
    candidateValue ++= candidates
  }

  def copyFrom() = {
    new Checker(this.value, this.index, this.candidateValue)
  }

  def getValue = {
    value
  }

  def getRowIndex = {
    index / Constant.BOARD_SIZE
  }

  def getColumnIndex = {
    index % Constant.BOARD_SIZE
  }

  def getRectIndex = {
    (getRowIndex / Constant.RECT_SIZE) * Constant.RECT_SIZE + getColumnIndex / Constant.RECT_SIZE
  }

  def hasValue = {
    value != Constant.NONE_VALUE
  }

  def removeCandidates(values: Array[Int]) = {
    candidateValue --= values
  }

  override  def toString() = {
    value.toString
  }

}
