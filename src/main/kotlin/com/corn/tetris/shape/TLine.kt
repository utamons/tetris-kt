package com.corn.tetris.shape

import javafx.geometry.Point2D

class TLine(private val cellSize: Double, private val gap: Double, basePoint: Point2D) : TShape(cellSize, gap, basePoint) {

    init {
        val yShift = gap / 2
        for (i in (0..4)) {
            rect(i*(cellSize + gap), yShift)
        }
    }
}