package com.corn.tetris.shape

import com.corn.tetris.CELL_G
import com.corn.tetris.GAP
import javafx.geometry.Point2D

class TLine(basePoint: Point2D) : TShape(basePoint) {

    private val cells = 5

    override fun probeTo(basepoint: Point2D): TShape {
        val shape = TLine(basepoint)
        shape.children.clear()
        val yShift = GAP / 2
        (0 until cells).forEach { i ->
            probeRect(i * CELL_G, yShift, shape)
        }
        return shape
    }

    override fun vCells(): Int {
        return 1
    }

    override fun hCells(): Int {
        return cells
    }

    init {
        val yShift = GAP / 2
        (0 until cells).forEach { i ->
            rect(i * CELL_G, yShift)
        }
    }
}