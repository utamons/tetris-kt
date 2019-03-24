package com.corn.tetris.shape

import com.corn.tetris.CELL_G
import com.corn.tetris.GAP
import javafx.geometry.Point2D

class TLine : TShape() {

    private val cells = 5

    override fun probeTo(basepoint: Point2D): TShape {
        val shape = TLine()
        shape.layoutX = basepoint.x
        shape.layoutY = basepoint.y
        shape.children.clear()
        val yShift = GAP / 2
        (0 until cells).forEach { i ->
            probeRect(i * CELL_G, yShift, shape)
        }
        return shape
    }

    override fun pivot(): Point2D {
        return Point2D(hCells() * CELL_G / 2 - GAP / 2, vCells() * CELL_G / 2)
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