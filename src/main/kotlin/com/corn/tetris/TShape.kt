package com.corn.tetris

import javafx.geometry.Point2D
import javafx.scene.Group
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle

class TShape(private val cellSize: Double, private val gap: Double, basePoint: Point2D) : Group() {

    private val color = Color.DARKRED

    init {
        rect(0.0, gap / 2)
        rect(cellSize + gap, gap / 2)
        rect((cellSize + gap) * 2, gap / 2)
        layoutX = basePoint.x
        layoutY = basePoint.y
    }

    private fun rect(x: Double, y: Double) {
        val rect = Rectangle(cellSize, cellSize)
        rect.arcHeight = 20.0
        rect.arcWidth = 20.0
        rect.fill = color
        rect.x = x
        rect.y = y
        children.add(rect)
    }

    fun pivot() : Point2D {
        val x = cellSize + gap + cellSize /2
        val y = gap / 2 + cellSize /2
        return Point2D(x,y)
    }

}