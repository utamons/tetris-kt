package com.corn.tetris

import javafx.scene.Group
import javafx.scene.paint.Color
import javafx.scene.shape.Line

class TContainer() : Group() {

    private val x1 = 70.0
    private val y1 = 100.0
    private val width = 500.0
    private val height = 900.0
    private val color = Color.BROWN
    private val lWidth = 10.0

    init {
        line(x1,y1,x1,y1+height)
        line(x1,y1+height,x1+width,y1+height)
        line(x1+width,y1,x1+width,y1+height)
    }

    fun line(x1 : Double,y1 : Double ,x2 : Double, y2: Double) {
        val line =   Line(x1,y1,x2,y2)
        line.stroke = color
        line.strokeWidth = lWidth
        children.add(line)
    }
}