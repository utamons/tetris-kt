package com.corn.tetris.shape

import com.corn.tetris.CELL_G
import com.corn.tetris.CELL_SIZE
import com.corn.tetris.COLS
import com.corn.tetris.GAP
import javafx.animation.KeyFrame
import javafx.animation.KeyValue
import javafx.animation.PathTransition
import javafx.animation.Timeline
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
    private var angle: Double = 0.0

    fun startPoint(startPoint: Point2D) {
        layoutX = 0.0
        layoutY = 0.0
        this.centerX = startPoint.x + (COLS / 2 - hCells() / 2) * (CELL_G) + (CELL_G) / 2 * hCells() - GAP / 2
        this.centerY = startPoint.y + (CELL_G) / 2 * vCells() - CELL_G * vCells()
        nextY = centerY + CELL_G
    }

    fun updatePoint() {
        centerY = (CELL_G) / 2 * vCells() + translateY
        centerX = CELL_G * hCells() / 2 + GAP / 2 + translateX - GAP
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
    abstract fun pivot(): Point2D

    private fun shape(angle: Double): TShape {
        val pX = toLeftEdge()
        val probe = probeTo(Point2D(pX, centerY - vCells() * CELL_G / 2))
        if (angle % 360 != 0.0) {
            val pvX = pivot().x
            val pvY = pivot().y
            val rotate = Rotate()
            val circle = Circle(pvX, pvY, GAP, Color.BLACK)
            probe.children.add(circle)
            rotate.angle = angle
            rotate.pivotX = pvX
            rotate.pivotY = pvY
            probe.transforms.add(rotate)
        }
        return probe;
    }

    fun deltaRotate(delta: Double): TShape {
        return shape(this.angle + delta);
    }

    fun shapeForFix(): TShape {
        val probe = shape(angle)
        probe.translateY += CELL_G
        return probe
    }

    fun shapeDown(): TShape {
        if (angle % 360 == 0.0) {
            val pX = toLeftEdge()
            return probeTo(Point2D(pX, centerY + CELL_G))
        } else {
            val probe = shape(angle)
            probe.translateY += (CELL_G + CELL_G / 2)
            return probe;
        }
    }

    fun shapeRight(): TShape {
        if (angle % 360 == 0.0) {
            val pX = toLeftEdge()
            return probeTo(Point2D(pX + CELL_G, centerY))
        } else {
            val probe = shape(angle)
            probe.translateX += CELL_G
            return probe;
        }
    }

    fun shapeLeft(): TShape {
        if (angle % 360 == 0.0) {
            val pX = toLeftEdge()
            return probeTo(Point2D(pX - CELL_G, centerY))
        } else {
            val probe = shape(angle)
            probe.translateX -= CELL_G
            return probe;
        }
    }


    fun rotate(angle: Double): Timeline {
        this.angle += angle
        val rotationTransform = Rotate(0.0, pivot().x, pivot().y)
        this.transforms.add(rotationTransform)
        val rotationAnimation = Timeline()
        rotationAnimation.keyFrames
                .add(
                        KeyFrame(
                                Duration.millis(100.0),
                                KeyValue(
                                        rotationTransform.angleProperty(),
                                        angle
                                )
                        )
                )
        rotationAnimation.cycleCount = 1
        rotationAnimation.isAutoReverse = false
        rotationAnimation.play()
        return rotationAnimation;
    }

    fun moveDown(): PathTransition {
        return move(path(centerX, nextY), 200.0)
    }

    fun moveRight(): PathTransition {
        return move(path(centerX + CELL_G, centerY), 100.0)
    }

    fun moveLeft(): PathTransition {
        return move(path(centerX - CELL_G, centerY), 100.0)
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
        var delta = 0.0

        if (nextY - centerY < 1.0) {
            delta = 1.0
        }

        val moveTo = MoveTo(centerX, centerY - delta)
        val linetTo = LineTo(nextX, nextY)

        path.elements.add(moveTo)
        path.elements.add(linetTo)

        return path
    }

    private fun toLeftEdge() = centerX - CELL_G * hCells() / 2 + GAP / 2
}