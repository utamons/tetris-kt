package com.corn.tetris.shape

import javafx.geometry.Point2D

class TLine(private val cells : Int, private val cellSize: Double, private val gap: Double, basePoint: Point2D) : TShape(cellSize, gap, basePoint) {

    override fun probeTo(basepoint: Point2D): TShape {
        val shape = TLine(cells,cellSize,gap,basepoint)
        shape.children.clear()
        val yShift = gap / 2
        for (i in 0 until cells) {
            probeRect(i*(cellSize + gap), yShift,shape)
        }
        return shape
    }

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