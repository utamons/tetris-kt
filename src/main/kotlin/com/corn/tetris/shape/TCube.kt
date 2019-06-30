package com.corn.tetris.shape

import com.corn.tetris.CELL_G
import com.corn.tetris.GAP
import javafx.geometry.Point2D

class TCube : TShape() {

    private val yShift = GAP / 2

    override fun pivot(): Point2D {
        return Point2D(hCells() * CELL_G / 2 - GAP / 2, vCells() * CELL_G / 2)
    }

    override fun vCells(): Int {
        return 2
    }

    override fun hCells(): Int {
        return 2
    }

    init {
        (0..1).forEach { i ->
            rect(i * CELL_G, yShift)
        }
        (0..1).forEach { i ->
            rect(i * CELL_G, yShift + CELL_G)
        }
    }

}