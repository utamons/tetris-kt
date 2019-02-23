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
                val x = col * (cellSize + 2*gap)
                val y = row * (cellSize + 2*gap)

                val circle = Circle(x, y, dotRadius, color)
                val c = Circle(x, y, 2.0, Color.BLANCHEDALMOND)
                children.add(circle)
                children.add(c)
            }
        }

    }
}