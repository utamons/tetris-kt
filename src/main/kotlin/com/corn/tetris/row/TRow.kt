package com.corn.tetris.row

import com.corn.tetris.CELL_G
import com.corn.tetris.CELL_SIZE
import com.corn.tetris.COLS
import com.corn.tetris.GAP
import com.corn.tetris.shape.TShape
import javafx.animation.ScaleTransition
import javafx.event.EventHandler
import javafx.geometry.Bounds
import javafx.geometry.Point2D
import javafx.scene.Group
import javafx.scene.Node
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.util.Duration
import java.util.*


class TRow(basePoint: Point2D) : Group() {

    private val empty = ArrayList<Rectangle>()
    private val fill = ArrayList<Rectangle>()
    var centerX: Double = 0.0
    var centerY: Double = 0.0
    private var disappearCounter = 0

    var idx = 0

    init {
        (0 until COLS).forEach { i ->
            rect(i * CELL_G, GAP / 2)
        }
        layoutX = basePoint.x
        layoutY = basePoint.y
        this.centerX = (CELL_G) / 2 * COLS - GAP / 2
        this.centerY = (CELL_G) / 2
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
        val toMove = ArrayList<Node>()
        shape.children.forEach { sChild ->
            empty.filter { it.intersects(cellBounds(sChild)) && !toMove.contains(it) }
                    .forEach {
                        toMove.add(it)
                        fill.add(it)
                        it.fill = Color.VIOLET
                    }
        }
        empty.removeAll(toMove)
    }

    fun isFull(): Boolean {
        return fill.size >= COLS
    }

    fun disappear(listener: () -> Unit) {
        disappearCounter = COLS
        fill.onEach {
            val scaleTransition = ScaleTransition()
            scaleTransition.duration = Duration.millis(150.0)
            scaleTransition.node = it
            scaleTransition.byY = -1.0
            scaleTransition.byX = -1.0
            scaleTransition.cycleCount = 1
            scaleTransition.isAutoReverse = false
            scaleTransition.play()
            scaleTransition.onFinished = EventHandler {
                if (--disappearCounter == 0) {
                    listener()
                }
            }
        }
    }
}