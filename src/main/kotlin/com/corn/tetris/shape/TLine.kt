package com.corn.tetris.shape

import javafx.geometry.Point2D

class TLine(private val cells : Int, private val cellSize: Double, private val gap: Double, basePoint: Point2D) : TShape(cellSize, gap, basePoint) {
    override fun vCells(): Int {
        return 1
    }

    override fun hCells(): Int {
        return cells;
    }

    init {
        val yShift = gap / 2
        for (i in 0 until cells) {
            rect(i*(cellSize + gap), yShift)
        }
    }
}