package com.corn.tetris

import com.corn.tetris.shape.TShape
import javafx.geometry.BoundingBox
import javafx.geometry.Bounds
import javafx.geometry.Point2D
import javafx.scene.Group
import javafx.scene.Node
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle

class TRow(private val basePoint: Point2D) : Group() {

    val empty = ArrayList<Node>()
    val fill = ArrayList<Node>()

    init {
        for (i in (0..(COLS-1))) {
            rect(i*(CELL_SIZE+ GAP), GAP/2)
        }
        layoutX = basePoint.x
        layoutY = basePoint.y
    }

    fun bounds() : Bounds{
        return BoundingBox(basePoint.x,basePoint.y,(CELL_SIZE+ GAP)*COLS, CELL_SIZE+ GAP)
    }

    private fun rect(x: Double, y: Double) {
        val rect = Rectangle(CELL_SIZE, CELL_SIZE)
        rect.arcHeight = 20.0
        rect.arcWidth = 20.0
        rect.fill = Color.TRANSPARENT
        rect.x = x
        rect.y = y
        children.add(rect)
        empty.add(rect)
    }

    fun canFit(shape: TShape): Boolean {
        for (child in fill) {
            for (sChild in shape.children) {
                if (child.intersects(sceneToLocal(sChild.localToScene(sChild.boundsInLocal)))) {
                    return false
                }
            }
        }
        return true
    }

    fun fix(shape: TShape) {
        val toRemove = ArrayList<Node>()
        val toMove = ArrayList<Node>()
        for (sChild in shape.children) {
            for (cell in empty) {
                if (cell.intersects(sceneToLocal(sChild.localToScene(sChild.boundsInLocal)))) {
                    toRemove.add(sChild);
                    (cell as Rectangle).fill = Color.VIOLET
                    toMove.add(cell)
                    fill.add(cell)
                }
            }
        }
        empty.removeAll(toMove)
        shape.children.removeAll(toRemove)
    }
}