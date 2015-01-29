package com.rover.sudoku

/**
 * Created by Dong Yan on 15/1/24.
 */

import org.scalatest.FlatSpec

import scala.collection.mutable.ArrayBuffer

class CheckerSpec extends FlatSpec {
  // tests go here...


  "A Checker" can "get position by index" in {
    var checker = new Checker(9, 0)
    assert(checker.getRowIndex == 0)
    assert(checker.getColumnIndex == 0)
    assert(checker.getRectIndex == 0)

    checker = new Checker(9, 4)
    assert(checker.getRowIndex == 0)
    assert(checker.getColumnIndex == 4)
    assert(checker.getRectIndex == 1)

    checker = new Checker(9, 9)
    assert(checker.getRowIndex == 1)
    assert(checker.getColumnIndex == 0)
    assert(checker.getRectIndex == 0)

    checker = new Checker(9, 15)
    assert(checker.getRowIndex == 1)
    assert(checker.getColumnIndex == 6)
    assert(checker.getRectIndex == 2)

  }
}
