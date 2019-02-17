package com.corn.tetris

import javafx.scene.Group
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle

class TShape() : Group() {

    init {
        val r1 = Rectangle(30.0,30.0)
        val r3 = Rectangle(30.0,30.0)
        val r2 = Rectangle(30.0,30.0)
        r1.fill = Color.RED
        r3.fill = Color.RED
        r2.fill = Color.BLUE
        r1.x = 0.0
        r1.y = 0.0
        r2.x = 33.0
        r2.y = 0.0
        r3.x = 66.0
        r3.y = 0.0
        children.add(r1)
    }
}