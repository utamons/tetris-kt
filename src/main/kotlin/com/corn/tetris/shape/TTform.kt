package com.corn.tetris.shape

import com.corn.tetris.CELL_G
import com.corn.tetris.GAP
import javafx.geometry.Point2D

class TTform : TShape() {

    private val yShift = GAP / 2

    override fun probeTo(basePoint: Point2D): TShape {
        val shape = TTform()
        shape.children.clear()
        (0..2).forEach { i ->
            probeRect(i * CELL_G, yShift, shape)
        }
        probeRect(CELL_G, yShift + CELL_G, shape)
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
        rect(CELL_G, yShift + CELL_G)

    }

}