package com.corn.tetris

import com.corn.tetris.shape.TShape
import javafx.geometry.Bounds
import javafx.geometry.Point2D
import javafx.scene.Group
import javafx.scene.Node
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle

class TRow(basePoint: Point2D) : Group() {

    private val empty = ArrayList<Rectangle>()
    private val fill = ArrayList<Rectangle>()

    init {
        (0 until COLS).forEach { i ->
            rect(i * CELL_G, GAP / 2)
        }
        layoutX = basePoint.x
        layoutY = basePoint.y
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
        return fill.none { child ->
            shape.children.any { child.intersects(cellBounds(it)) }
        }
    }

    private fun cellBounds(cell: Node): Bounds {
        return sceneToLocal(cell.localToScene(cell.boundsInLocal))
    }

    fun fix(shape: TShape) {
        val probe = shape.shapeForFix()
        val toRemove = ArrayList<Node>()
        val toMove = ArrayList<Node>()
        probe.children.forEach { sChild ->
            empty.filter { it.intersects(cellBounds(sChild)) }
                    .forEach {
                        toRemove.add(sChild)
                        toMove.add(it)
                        fill.add(it)
                        it.fill = Color.VIOLET
                    }
        }
        empty.removeAll(toMove)
        /*shape.children.removeAll(toRemove)*/
    }
}