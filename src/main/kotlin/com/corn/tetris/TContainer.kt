package com.corn.tetris

import javafx.geometry.Point2D
import javafx.scene.Group
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.scene.shape.Line
import javafx.scene.shape.StrokeLineCap

class TContainer(cols: Int, rows: Int, gap: Double, cellSize: Double, basePoint: Point2D) : Group() {

    private val x1 = 0.0
    private val y1 = 0.0
    private val lWidth = 10.0
    private val width = initSize(cols, gap, cellSize) + gap * 4
    private val height = initSize(rows, gap, cellSize) + (lWidth + gap) / 2.0
    private val color = Color.BROWN

    private val tGrid = TGrid(cols, rows, gap, cellSize, Point2D((lWidth / 2 + gap), 0.0))

    init {
        line(x1, y1, x1, y1 + height)
        line(x1, y1 + height, x1 + width, y1 + height)
        line(x1 + width, y1, x1 + width, y1 + height)
        val c = Circle(x1, y1, 2.0, Color.BLANCHEDALMOND)
        layoutX = basePoint.x
        layoutY = basePoint.y
        children.add(c)
        children.add(tGrid)
    }

    private fun initSize(cells: Int, gap: Double, cellSize: Double): Double {
        return (cells * (cellSize + gap))
    }


    private fun line(x1: Double, y1: Double, x2: Double, y2: Double) {
        val line = Line(x1, y1, x2, y2)
        line.stroke = color
        line.strokeLineCap = StrokeLineCap.ROUND
        line.strokeWidth = lWidth
        children.add(line)
    }
}