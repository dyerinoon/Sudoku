package com.rover.sudoku

import scala.collection.mutable.ArrayBuffer

/**
 * Created by Dong Yan on 15/1/23.
 */
class Checker(var value: Int, val index: Int) {

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

  def update() = {
    if (candidateValue.size == 1) {
      this.value = candidateValue(0)
      candidateValue.clear
    }
  }

  def updateInGroup(group: ArrayBuffer[Checker]) = {
    if(!hasValue) {
      group.filter(it => it.hasValue).map(it => {
        this.candidateValue -= it.getValue
      })

      if (candidateValue.size == 1) {
        this.value = candidateValue(0)
        candidateValue.clear
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


}
