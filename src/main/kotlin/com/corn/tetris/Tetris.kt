package com.corn.tetris

import javafx.geometry.Point2D
import javafx.scene.Group

const val CELL_SIZE = 50.0
const val GAP = 4.0
const val COLS = 10
const val ROWS = 18

class Tetris : Group() {

    private val container: TContainer = TContainer(COLS, ROWS, GAP, CELL_SIZE, Point2D(70.0,100.0))
    private val row: TRow = TRow(70.0,100.0, CELL_SIZE, GAP)

    init {
        children.add(container)
        children.add(row)
    }

}