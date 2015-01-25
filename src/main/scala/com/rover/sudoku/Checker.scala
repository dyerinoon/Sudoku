package com.rover.sudoku

import scala.collection.mutable.ArrayBuffer

/**
 * Created by Dong Yan on 15/1/23.
 */
class Checker(var value: Int, val index: Int) {

  private val LOG = org.apache.log4j.Logger.getLogger("Checker")

  private val candidateValue  = ArrayBuffer(1, 2, 3, 4, 5, 6, 7, 8, 9)
  if (value != Constant.NONE_VALUE) {
    candidateValue.clear
  }

  def getValue = { value }
  def getRowIndex = { index / Constant.BOARD_SIZE}
  def getColumnIndex = { index % Constant.BOARD_SIZE }

  def getRectIndex = {
    (getRowIndex / Constant.RECT_SIZE) * Constant.RECT_SIZE + getColumnIndex / Constant.RECT_SIZE
  }

  def getIndex = { index }

  def update() = {
    if (candidateValue.size == 1) {
      this.value = candidateValue(0)
      candidateValue.clear
    }
  }

  def updateInGroup(group: Array[Checker]) = {
    if(!hasValue) {
      group.filter(it => it.hasValue).map(it => {
        this.candidateValue -= it.getValue
      })

      if (candidateValue.size == 1) {
        this.value = candidateValue(0)
        candidateValue.clear

        LOG.info ("(" + getRowIndex + ", " + getColumnIndex + "), new value: " + value)

        true
      }else {
        false
      }
    } else {
      false
    }
  }

  def hasValue = {value != Constant.NONE_VALUE}

  def removeCandidates(values: Array[Int]) = {
    candidateValue --= values
  }

  override def toString = {
    value.toString
  }

}
