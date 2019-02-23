package com.corn.tetris

import javafx.geometry.Point2D

const val CELL_SIZE = 50.0
const val GAP = 3.0
const val COLS = 10
const val ROWS = 18

class Tetris {

    val container: TContainer = TContainer(COLS, ROWS, GAP, CELL_SIZE, Point2D(70.0,100.0))

}