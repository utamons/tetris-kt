package com.corn.tetris.shape

import com.corn.tetris.CELL_G
import com.corn.tetris.CELL_SIZE
import javafx.animation.Animation.INDEFINITE
import javafx.animation.PathTransition
import javafx.geometry.Point2D
import javafx.scene.Group
import javafx.scene.paint.Color
import javafx.scene.shape.LineTo
import javafx.scene.shape.MoveTo
import javafx.scene.shape.Path
import javafx.scene.shape.Rectangle
import javafx.util.Duration

abstract class TShape() : Group() {

    private val color = Color.DARKGREEN

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
        rect.fill = Color.RED
        rect.x = x
        rect.y = y
        shape.children.add(rect)
    }

    abstract fun hCells(): Int
    abstract fun vCells(): Int
    abstract fun probeTo(basepoint: Point2D): TShape

    fun shapeDown(x: Double, y: Double): TShape {
        return probeTo(Point2D(x, y))
    }

    private fun pathDown(x: Double, y: Double): Path {
        val path = Path()

        val moveTo = MoveTo(x, y)
        val linetTo = LineTo(x, y + CELL_G)

        path.elements.add(moveTo)
        path.elements.add(linetTo)

        return path
    }

    fun moveDown(x: Double, y: Double): PathTransition {
        val ptr = PathTransition()
        ptr.duration = Duration.millis(500.0)
        ptr.node = this
        ptr.path = pathDown(x, y)
        ptr.cycleCount = INDEFINITE
        ptr.isAutoReverse = true;
        ptr.play()
        return ptr
    }
}