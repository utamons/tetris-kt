package com.corn.tetris

import javafx.geometry.Point2D
import javafx.scene.Group

const val CELL_SIZE = 50.0
const val GAP = 4.0
const val COLS = 10
const val ROWS = 17
const val L_WIDTH = 10.0

class Tetris : Group() {

    private val container: TContainer = TContainer(COLS, ROWS, GAP, CELL_SIZE, L_WIDTH, Point2D(70.0,80.0))

    init {
        children.add(container)
        /*for (i in (0..17)) {
            children.add(TRow(70.0+(GAP*2)+1, 100.0 + i*(CELL_SIZE + GAP), CELL_SIZE, GAP))
        }*/
    }

}