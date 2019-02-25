package com.corn.tetris.shape

import javafx.geometry.Point2D
import javafx.scene.Group
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle

abstract class TShape(private val cellSize: Double, private val gap: Double, basePoint: Point2D) : Group() {

    private val color = Color.DARKGREEN

    init {
        layoutX = basePoint.x
        layoutY = basePoint.y
    }

    protected fun rect(x: Double, y: Double) {
        val rect = Rectangle(cellSize, cellSize)
        rect.arcHeight = 20.0
        rect.arcWidth = 20.0
        rect.fill = color
        rect.x = x
        rect.y = y
        children.add(rect)
    }

    fun pivot() : Point2D {
        val x = boundsInLocal.width/2.0
        val y = gap / 2 + boundsInLocal.height/2.0
        return Point2D(x,y)
    }

    public abstract fun hCells() : Int
}