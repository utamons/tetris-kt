package com.corn.tetris

import javafx.geometry.Point2D
import javafx.scene.Group
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle

class TRow(cols: Int, private val cellSize: Double, gap: Double, basePoint: Point2D) : Group() {

    private val color = Color.DARKGREEN

    init {
        for (i in (0..(cols-1))) {
            rect(i*(cellSize+gap), gap/2)
        }
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

}