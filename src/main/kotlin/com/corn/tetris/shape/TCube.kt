package com.corn.tetris.shape

import javafx.geometry.Point2D

class TCube(private val cellSize: Double, private val gap: Double, basePoint: Point2D) : TShape(cellSize, gap, basePoint) {

    override fun probeTo(basepoint: Point2D): TShape {
        val shape = TCube(cellSize, gap, basepoint)
        shape.children.clear()
        val yShift = gap / 2
        for (i in (0..1)) {
            probeRect(i * (cellSize + gap), yShift, shape)
        }
        for (i in (0..1)) {
            probeRect(i * (cellSize + gap), yShift + cellSize + gap, shape)
        }
        return shape
    }

    override fun vCells(): Int {
        return 2;
    }

    override fun hCells(): Int {
        return 2;
    }

    init {
        val yShift = gap / 2
        for (i in (0..1)) {
            rect(i * (cellSize + gap), yShift)
        }
        for (i in (0..1)) {
            rect(i * (cellSize + gap), yShift + cellSize + gap)
        }
    }
}