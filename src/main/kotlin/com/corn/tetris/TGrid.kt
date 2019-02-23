package com.corn.tetris

import javafx.geometry.Point2D
import javafx.scene.Group
import javafx.scene.paint.Color
import javafx.scene.shape.Circle

class TGrid(cols: Int, rows: Int, gap: Double, cellSize: Double, basePoint: Point2D) : Group() {

    private val color = Color.BLUE
    private val dotRadius = gap

    init {
        layoutX = basePoint.x
        layoutY = basePoint.y

        for (row in (0..rows)) {
            for (col in (0..cols)) {
                val x = col * (cellSize + gap) - (if (col == 0) 0.0 else gap / 2)
                val y = row * (cellSize + gap) - (if (row == 0) 0.0 else gap / 2)

                val circle = Circle(x, y, dotRadius, color)
                val c = Circle(x, y, 2.0, Color.BLANCHEDALMOND)
                children.add(circle)
                children.add(c)
            }
        }

    }
}