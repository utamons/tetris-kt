package com.corn.tetris

import javafx.geometry.Point2D
import javafx.scene.Group

const val CELL_SIZE = 50.0
const val GAP = 4.0
const val COLS = 10
const val ROWS = 18

class Tetris : Group() {

    private val container: TContainer = TContainer(COLS, ROWS, GAP, CELL_SIZE, Point2D(70.0,100.0))
    private val row1: TRow = TRow(70.0,100.0, CELL_SIZE, GAP)
    private val row2: TRow = TRow(70.0,100.0 + CELL_SIZE + GAP, CELL_SIZE, GAP)

    init {
        children.add(container)
        for (i in (0..17)) {
            children.add(TRow(70.0+(GAP*2)+1, 100.0 + i*(CELL_SIZE + GAP), CELL_SIZE, GAP))
        }
    }

}