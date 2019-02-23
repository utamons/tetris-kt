package com.corn.tetris

import javafx.geometry.Point2D
import javafx.scene.Group
import javafx.scene.paint.Color
import javafx.scene.shape.Circle

class TGrid(cols: Int, rows: Int, gap: Double, cellSize: Double, basePoint: Point2D) : Group() {

    private val color = Color.BLUE
    private val dotRadius = gap / 2

    init {
        layoutX = basePoint.x
        layoutY = basePoint.y

        for (row in (0..rows)) {
            for (col in (0..cols)) {
                val x = col * (cellSize + gap)
                val y = row * (cellSize + gap)

                val circle = Circle(x, y, dotRadius, Color.BLANCHEDALMOND)
                circle.stroke = color
                children.add(circle)
            }
        }

    }
}