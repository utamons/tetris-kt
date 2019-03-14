package com.corn.tetris.shape

import com.corn.tetris.CELL_G
import com.corn.tetris.GAP
import javafx.geometry.Point2D

class TCube : TShape() {

    private val yShift = GAP / 2

    override fun probeTo(basepoint: Point2D): TShape {
        val shape = TCube()
        shape.layoutX = basepoint.x
        shape.layoutY = basepoint.y
        shape.children.clear()
        (0..1).forEach { i ->
            probeRect(i * CELL_G, yShift, shape)
        }
        (0..1).forEach { i ->
            probeRect(i * CELL_G, yShift + CELL_G, shape)
        }
        return shape
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