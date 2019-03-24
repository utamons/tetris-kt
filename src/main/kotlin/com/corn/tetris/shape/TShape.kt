package com.corn.tetris.shape

import com.corn.tetris.CELL_G
import com.corn.tetris.CELL_SIZE
import com.corn.tetris.COLS
import com.corn.tetris.GAP
import javafx.animation.PathTransition
import javafx.animation.RotateTransition
import javafx.geometry.Point2D
import javafx.scene.Group
import javafx.scene.paint.Color
import javafx.scene.shape.*
import javafx.scene.transform.Rotate
import javafx.util.Duration

abstract class TShape : Group() {

    private var centerX: Double = 0.0
    private var centerY: Double = 0.0
    private var nextY: Double = 0.0

    fun startPoint(startPoint: Point2D) {
        layoutX = 0.0
        layoutY = 0.0
        this.centerX = startPoint.x + (COLS / 2 - hCells() / 2) * (CELL_G) + (CELL_G) / 2 * hCells() - GAP / 2
        this.centerY = startPoint.y + (CELL_G) / 2 * vCells() - CELL_G * vCells()
        nextY = centerY + CELL_G
    }

    fun updatePoint(): Circle {
        centerY = (CELL_G) / 2 * vCells() + translateY
        centerX = CELL_G * hCells() / 2 + GAP / 2 + translateX - GAP
        val circle = Circle(centerX, centerY, GAP, Color.VIOLET)
        circle.stroke = Color.ALICEBLUE
        return circle;
    }

    fun setNextY() {
        nextY = centerY + CELL_G
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
        val pX = centerX - CELL_G * hCells() / 2 + GAP / 2
        return probeTo(Point2D(pX, centerY + CELL_G))
    }

    fun shapeRotate(): TShape {
        val pX = centerX - CELL_G * hCells() / 2 + GAP / 2
        val probe = probeTo(Point2D(pX, centerY-vCells()* CELL_G/2 ))
        val rotate = Rotate()
        val pvX = probe.hCells() * CELL_G/2 - GAP/2
        val pvY = probe.vCells()* CELL_G/2
        val circle = Circle(pvX, pvY, GAP, Color.BLACK)
        //circle.stroke = color
        probe.children.add(circle)
        /*rotate.angle = -90.0;
        rotate.pivotX = pvX
        rotate.pivotY = pvY
        probe.transforms.add(rotate)*/
        return probe;
    }

    fun shapeRight(): TShape {
        val pX = centerX - CELL_G * hCells() / 2 + GAP / 2
        return probeTo(Point2D(pX + CELL_G, centerY))
    }

    fun shapeLeft(): TShape {
        val pX = centerX - CELL_G * hCells() / 2 + GAP / 2
        return probeTo(Point2D(pX - CELL_G, centerY))
    }

    fun moveDown(): PathTransition {
        return move(path(centerX,nextY),1000.0)
    }

    fun moveRight(): PathTransition {
        return move(path(centerX + CELL_G, centerY),100.0)
    }

    fun moveLeft(): PathTransition {
        return move(path(centerX - CELL_G, centerY),100.0)
    }

    private fun move(path: Path, duration: Double): PathTransition {
        val ptr = PathTransition()
        ptr.duration = Duration.millis(duration)
        ptr.node = this
        ptr.path = path
        ptr.cycleCount = 1
        ptr.play()
        return ptr
    }

    private fun path(nextX: Double, nextY: Double): Path {
        val path = Path()

        val moveTo = MoveTo(centerX, centerY)
        val linetTo = LineTo(nextX, nextY)

        path.elements.add(moveTo)
        path.elements.add(linetTo)

        return path
    }

}