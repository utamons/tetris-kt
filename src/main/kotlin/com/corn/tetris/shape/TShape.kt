package com.corn.tetris.shape

import com.corn.tetris.CELL_G
import com.corn.tetris.CELL_SIZE
import com.corn.tetris.COLS
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

abstract class TShape : Group() {

    private var x: Double = 0.0
    private var y: Double = 0.0

    fun startPoint(startPoint: Point2D) {
        layoutX = 0.0
        layoutY = 0.0
        this.x = startPoint.x + (COLS / 2 - hCells() / 2) * (CELL_G) + (CELL_G) / 2 * hCells() - GAP / 2
        this.y = startPoint.y + (CELL_G) / 2 * vCells() - CELL_G * vCells()
    }

    fun updatePoint() {
        y = (CELL_G) / 2 * vCells() + translateY
        x = CELL_G * hCells() / 2 + GAP / 2 + translateX - GAP
    }

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

    fun shapeDown(): TShape {
        val pX = x - CELL_G * hCells() / 2 + GAP / 2
        return probeTo(Point2D(pX, y + CELL_G))
    }

    fun shapeRight(): TShape {
        val pX = x - CELL_G * hCells() / 2 + GAP / 2
        return probeTo(Point2D(pX + CELL_G, y))
    }

    fun shapeLeft(): TShape {
        val pX = x - CELL_G * hCells() / 2 + GAP / 2
        return probeTo(Point2D(pX - CELL_G, y))
    }

    private fun pathDown(): Path {
        val path = Path()

        val moveTo = MoveTo(x, y)
        val linetTo = LineTo(x, y + CELL_G)

        path.elements.add(moveTo)
        path.elements.add(linetTo)

        return path
    }

    private fun pathRight(): Path {
        val path = Path()

        val moveTo = MoveTo(x, y)
        val linetTo = LineTo(x + CELL_G, y)

        path.elements.add(moveTo)
        path.elements.add(linetTo)

        return path
    }

    private fun pathLeft(): Path {
        val path = Path()

        val moveTo = MoveTo(x, y)
        val linetTo = LineTo(x - CELL_G, y)

        path.elements.add(moveTo)
        path.elements.add(linetTo)

        return path
    }

    fun moveDown(): PathTransition {
        val ptr = PathTransition()
        ptr.duration = Duration.millis(1000.0)
        ptr.node = this
        ptr.path = pathDown()
        ptr.cycleCount = 1
        ptr.play()
        return ptr
    }


    fun moveRight(): PathTransition {
        val ptr = PathTransition()
        ptr.duration = Duration.millis(100.0)
        ptr.node = this
        ptr.path = pathRight()
        ptr.cycleCount = 1
        ptr.play()
        return ptr
    }

    fun moveLeft(): PathTransition {
        val ptr = PathTransition()
        ptr.duration = Duration.millis(100.0)
        ptr.node = this
        ptr.path = pathLeft()
        ptr.cycleCount = 1
        ptr.play()
        return ptr
    }

}