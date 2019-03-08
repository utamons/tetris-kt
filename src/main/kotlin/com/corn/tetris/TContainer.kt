package com.corn.tetris

import com.corn.tetris.shape.TShape
import javafx.geometry.Bounds
import javafx.geometry.Point2D
import javafx.scene.Group
import javafx.scene.Node
import javafx.scene.paint.Color
import javafx.scene.shape.Line
import javafx.scene.shape.StrokeLineCap

class TContainer(basePoint: Point2D) : Group() {

    private val x1 = 0.0
    private val y1 = 0.0
    private val width = initSize(COLS)
    private val height = initSize(ROWS)
    private val color = Color.BROWN

    private val tGrid = TGrid(Point2D((L_WIDTH / 2 + GAP / 2), (L_WIDTH / 2 + GAP / 2)))

    init {
        line(x1, y1, x1, y1 + height)
        line(x1, y1 + height, x1 + width, y1 + height)
        line(x1 + width, y1, x1 + width, y1 + height)
        layoutX = basePoint.x
        layoutY = basePoint.y
        children.add(tGrid)
    }

    private fun initSize(cells: Int): Double {
        return (cells * CELL_G) + L_WIDTH + GAP
    }

    fun canFit(shape: TShape): Boolean {
        return children.none { child ->
            shape.children.any { child is Line && child.intersects(cellBounds(it)) }
        }
    }

    private fun cellBounds(cell: Node): Bounds {
        return sceneToLocal(cell.localToScene(cell.boundsInLocal))
    }

    private fun line(x1: Double, y1: Double, x2: Double, y2: Double) {
        val line = Line(x1, y1, x2, y2)
        line.stroke = color
        line.strokeLineCap = StrokeLineCap.ROUND
        line.strokeWidth = L_WIDTH
        children.add(line)
    }
}