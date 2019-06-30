package com.corn.tetris.shape

import com.corn.tetris.CELL_G
import com.corn.tetris.CELL_SIZE
import com.corn.tetris.COLS
import com.corn.tetris.GAP
import javafx.animation.KeyFrame
import javafx.animation.KeyValue
import javafx.animation.PathTransition
import javafx.animation.Timeline
import javafx.geometry.Bounds
import javafx.geometry.Point2D
import javafx.scene.Group
import javafx.scene.paint.Color
import javafx.scene.shape.*
import javafx.scene.transform.Rotate
import javafx.scene.transform.Translate
import javafx.util.Duration


abstract class TShape : Group() {

    private var centerX: Double = 0.0
    var centerY: Double = 0.0
    var nextY: Double = 0.0
    private var angle: Double = 0.0
    var speed = 300.0
    var rotateSpeed = 100.0

    fun startPoint(startPoint: Point2D) {
        layoutX = 0.0
        layoutY = 0.0
        this.centerX = centerX(startPoint)
        this.centerY = centerY(startPoint)
        nextY = centerY + CELL_G
    }

    private fun centerY(startPoint: Point2D) = startPoint.y + (CELL_G) / 2 * vCells() - CELL_G * vCells()

    private fun centerX(startPoint: Point2D) =
            startPoint.x + (COLS / 2 - hCells() / 2) * (CELL_G) + (CELL_G) / 2 * hCells() - GAP / 2


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

    abstract fun hCells(): Int
    abstract fun vCells(): Int
    abstract fun pivot(): Point2D

    fun allBounds() : List<Bounds> {
        return children.map {it.localToScene(it.boundsInLocal)}
    }

    fun boundsDown() : List<Bounds> {
        val tr = Translate();
        tr.y += CELL_G
        return children.map {
            val bounds = it.localToScene(it.boundsInLocal)
            tr.transform(bounds)
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
                                Duration.millis(rotateSpeed),
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
        return move(path(centerX, nextY), speed)
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
}