package com.corn.tetris

import javafx.scene.Group
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle

class TShape : Group() {

    private val color = Color.DARKGREEN
    private val rSize = 50.0;
    private val gap = 3.0;

    init {
        rect(0.0, 0.0)
        rect(rSize + gap, 0.0)
        rect((rSize + gap) * 2, 0.0)
    }

    private fun rect(x: Double, y: Double) {
        val rect = Rectangle(rSize, rSize)
        rect.arcHeight = 20.0
        rect.arcWidth = 20.0
        rect.fill = color
        rect.x = x
        rect.y = y
        children.add(rect)
    }

    public fun size() : Int {
        return children.size;
    }
}