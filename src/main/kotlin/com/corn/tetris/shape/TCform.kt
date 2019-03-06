package com.corn.tetris.shape

import com.corn.tetris.CELL_G
import com.corn.tetris.GAP
import javafx.geometry.Point2D

class TCform(basePoint: Point2D) : TShape(basePoint) {

    private val yShift = GAP / 2

    override fun probeTo(basepoint: Point2D): TShape {
        val shape = TCform(basepoint)
        shape.children.clear()
        (0..2).forEach { i ->
            probeRect(i * CELL_G, yShift, shape)
        }
        (0..2).forEach { i ->
            if ((i + 1) % 2 != 0)
                probeRect(i * CELL_G, yShift + CELL_G, shape)
        }
        return shape
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
        (0..2).forEach { i ->
            if ((i + 1) % 2 != 0)
                rect(i * CELL_G, yShift + CELL_G)
        }
    }

}