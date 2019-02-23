package com.corn.tetris

import javafx.geometry.Point2D
import javafx.scene.Group
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle

class TRow(cols: Int, private val cellSize: Double, gap: Double, basePoint: Point2D) : Group() {

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
        rect.fill = Color.DARKGREEN
        rect.x = x
        rect.y = y
        children.add(rect)
    }

    fun canFit(shape: TShape): Boolean {
        for (child in children) {
            for (sChild in shape.children) {
                if (child.intersects(sceneToLocal(sChild.localToScene(sChild.boundsInLocal)))) {
                    return false
                }
            }
        }
        return true
    }
}