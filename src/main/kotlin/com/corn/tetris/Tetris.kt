package com.corn.tetris

import javafx.geometry.Point2D
import javafx.scene.Group

const val CELL_SIZE = 50.0
const val GAP = 2.0
const val COLS = 8
const val ROWS = 17
const val L_WIDTH = 10.0

class Tetris : Group() {

    private val container: TContainer = TContainer(COLS, ROWS, GAP, CELL_SIZE, L_WIDTH, Point2D(70.0,80.0))

    init {
        children.add(container)
        for (i in (0..16)) {
            children.add(TRow(COLS, CELL_SIZE, GAP, Point2D(70.0+(L_WIDTH/2+2* GAP), 80.0 + (L_WIDTH / 2 + GAP) + i*(CELL_SIZE + 2*GAP))))
        }
    }

}