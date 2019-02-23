package com.corn.tetris

import javafx.scene.Group
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle

class TRow(x: Double, y: Double, private val cellSize: Double, gap: Double) : Group() {

    private val color = Color.DARKGREEN

    init {
        for (i in (0..9)) {
            rect(i*(cellSize+gap),0.0)
        }
        layoutX = x;
        layoutY = y;
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