package com.corn.tetris.shape

import com.corn.tetris.CELL_G
import com.corn.tetris.GAP
import javafx.geometry.Point2D

class TLform : TShape() {
    // test
    private val yShift = GAP / 2

    override fun probeTo(basepoint: Point2D): TShape {
        val shape = TLform()
        shape.layoutX = basepoint.x
        shape.layoutY = basepoint.y
        shape.children.clear()
        (0..2).forEach { i ->
            probeRect(i * CELL_G, yShift, shape)
        }
        probeRect(0.0, yShift + CELL_G, shape)
        return shape
    }

    override fun pivot(): Point2D {
        return Point2D(hCells() * CELL_G / 2 - GAP / 2, vCells() * CELL_G / 2 - CELL_G/2)
    }

    override fun vCells(): Int {
        return 2
    }

    override fun hCells(): Int {
        return 3
    }

    init {
        (0..2).forEach { i ->
            rect(i * CELL_G, yShift)
        }
        rect(0.0, yShift + CELL_G)

    }

}