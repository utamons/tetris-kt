package com.corn.tetris.shape

import com.corn.tetris.CELL_G
import com.corn.tetris.CELL_SIZE
import com.corn.tetris.GAP
import javafx.geometry.Point2D

class TCube(basePoint: Point2D) : TShape(basePoint) {

    override fun probeTo(basepoint: Point2D): TShape {
        val shape = TCube(basepoint)
        shape.children.clear()
        val yShift = GAP / 2
        for (i in (0..1)) {
            probeRect(i * (CELL_G), yShift, shape)
        }
        for (i in (0..1)) {
            probeRect(i * (CELL_G), yShift + CELL_G, shape)
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
        val yShift = GAP / 2
        for (i in (0..1)) {
            rect(i * (CELL_G), yShift)
        }
        for (i in (0..1)) {
            rect(i * (CELL_G), yShift + CELL_G)
        }
    }
}