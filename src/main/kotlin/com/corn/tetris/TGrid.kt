package com.corn.tetris

import javafx.geometry.Point2D
import javafx.scene.Group
import javafx.scene.paint.Color
import javafx.scene.shape.Circle

class TGrid(basePoint: Point2D) : Group() {

    private val color = Color.BLUE
    private val dotRadius = GAP / 2

    init {
        layoutX = basePoint.x
        layoutY = basePoint.y

        for (row in (0..ROWS)) {
            for (col in (0..COLS)) {
                val x = col * (CELL_G)
                val y = row * (CELL_G)

                val circle = Circle(x, y, dotRadius, Color.BLANCHEDALMOND)
                circle.stroke = color
                children.add(circle)
            }
        }

    }
}