package com.corn.tetris.shape

import com.corn.tetris.CELL_G
import com.corn.tetris.CELL_SIZE
import com.corn.tetris.GAP
import javafx.animation.PathTransition
import javafx.geometry.Point2D
import javafx.scene.Group
import javafx.scene.paint.Color
import javafx.scene.shape.LineTo
import javafx.scene.shape.MoveTo
import javafx.scene.shape.Path
import javafx.scene.shape.Rectangle
import javafx.util.Duration

abstract class TShape(basePoint: Point2D) : Group() {

    private val color = Color.DARKGREEN

    init {
        layoutX = basePoint.x
        layoutY = basePoint.y
    }

    protected fun rect(x: Double, y: Double) {
        val rect = Rectangle(CELL_SIZE, CELL_SIZE)
        rect.arcHeight = CELL_SIZE * 0.44
        rect.arcWidth = rect.arcHeight
        rect.fill = color
        rect.x = x
        rect.y = y
        children.add(rect)
    }

    protected fun probeRect(x: Double, y: Double, shape: TShape) {
        val rect = Rectangle(CELL_SIZE, CELL_SIZE)
        rect.fill = Color.TRANSPARENT
        rect.x = x
        rect.y = y
        shape.children.add(rect)
    }

    /*fun pivot() : Point2D {
        val x = boundsInLocal.width/2.0
        val y = GAP / 2 + boundsInLocal.height/2.0
        return Point2D(x,y)
    }*/

    abstract fun hCells(): Int
    abstract fun vCells(): Int
    abstract fun probeTo(basepoint: Point2D): TShape

    fun shapeDown(countV: Int, countH: Int): TShape {
        val x = layoutX + CELL_G * (countH - 1)
        val y = layoutY + (CELL_G) * (countV + 1)

        return probeTo(Point2D(x, y))
    }

    private fun pathDown(count: Int, countH: Int): Path {
        val path = Path()

        val startPt = Point2D((CELL_G) / 2 * hCells() - GAP / 2, (CELL_G) / 2 * vCells() - GAP / 2)
        val x = startPt.x + (CELL_G) * (countH - 1)
        val y = startPt.y + (CELL_G) * (count - 1) + GAP / 2

        val moveTo = MoveTo(x, y)
        val linetTo = LineTo(x, y + CELL_G)

        path.elements.add(moveTo)
        path.elements.add(linetTo)

        return path
    }

    private fun pathRight(countV: Int, countH: Int): Path {
        val path = Path()

        val startPt = Point2D((CELL_G) / 2 * hCells() - GAP / 2, (CELL_G) / 2 * vCells() - GAP / 2)
        val x = startPt.x + (CELL_G) * (countH - 1)
        val y = startPt.y + (CELL_G) * (countV - 1) + GAP / 2

        val moveTo = MoveTo(x, y)
        val linetTo = LineTo(x + CELL_G, y)

        path.elements.add(moveTo)
        path.elements.add(linetTo)

        return path
    }

    private fun pathLeft(countV: Int, countH: Int): Path {
        val path = Path()

        val startPt = Point2D((CELL_G) / 2 * hCells() - GAP / 2, (CELL_G) / 2 * vCells() - GAP / 2)
        val x = startPt.x + (CELL_G) * (countH - 1)
        val y = startPt.y + (CELL_G) * (countV - 1) + GAP / 2

        val moveTo = MoveTo(x, y)
        val linetTo = LineTo(x - CELL_G, y)

        path.elements.add(moveTo)
        path.elements.add(linetTo)

        return path
    }


    fun moveDown(countV: Int, countH: Int): PathTransition {
        val ptr = PathTransition()
        ptr.duration = Duration.millis(500.0)
        ptr.node = this
        ptr.path = pathDown(countV, countH)
        ptr.cycleCount = 1
        ptr.play()
        return ptr
    }

    fun shapeLeft(countV: Int, countH: Int): TShape {
        val x = layoutX + CELL_G * (countH - 2)
        val y = layoutY + CELL_G * countV

        return probeTo(Point2D(x, y))
    }

    fun moveLeft(countV: Int, countH: Int): PathTransition {
        val ptr = PathTransition()
        ptr.duration = Duration.millis(10.0)
        ptr.node = this
        ptr.path = pathLeft(countV, countH)
        ptr.cycleCount = 1
        ptr.play()
        return ptr
    }

    fun shapeRight(countV: Int, countH: Int): TShape {
        val x = layoutX + CELL_G * countH
        val y = layoutY + CELL_G * countV

        return probeTo(Point2D(x, y))
    }

    fun moveRight(countV: Int, countH: Int): PathTransition {
        val ptr = PathTransition()
        ptr.duration = Duration.millis(10.0)
        ptr.node = this
        ptr.path = pathRight(countV, countH)
        ptr.cycleCount = 1
        ptr.play()
        return ptr
    }
}