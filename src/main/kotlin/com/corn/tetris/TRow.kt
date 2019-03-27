package com.corn.tetris

import com.corn.tetris.shape.TShape
import javafx.animation.PathTransition
import javafx.geometry.Bounds
import javafx.geometry.Point2D
import javafx.scene.Group
import javafx.scene.Node
import javafx.scene.paint.Color
import javafx.scene.shape.*
import javafx.util.Duration
import java.text.SimpleDateFormat
import java.util.*

class TRow(basePoint: Point2D) : Group() {

    private val empty = ArrayList<Rectangle>()
    private val fill = ArrayList<Rectangle>()
    private var centerX: Double = 0.0
    private var centerY: Double = 0.0
    private val createdAt = Date();
    private val df = SimpleDateFormat("HH:mm:ss")

    fun log() : String {
        val dateStr = df.format(createdAt)
        return "${fill.size} $dateStr"
    }

    init {
        (0 until COLS).forEach { i ->
            rect(i * CELL_G, GAP / 2)
        }
        layoutX = basePoint.x
        layoutY = basePoint.y
        this.centerX = (CELL_G) / 2 * COLS - GAP / 2
        this.centerY = (CELL_G) / 2
        val circle = Circle(centerX, centerY, GAP, Color.BLACK)
        children.add(circle)
    }

    private fun updatePoint() {
        centerY = (CELL_G) / 2 + translateY
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
            empty.filter { it.intersects(cellBounds(sChild)) && !toMove.contains(it) }
                    .forEach {
                        toRemove.add(sChild)
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

    fun fall(count: Int): PathTransition {
        val tr = move(count)
        updatePoint()
        return tr
    }

    private fun path(count: Int): Path {
        val path = Path()

        val moveTo = MoveTo(centerX, centerY)
        val linetTo = LineTo(centerX, centerY + CELL_G * count)

        path.elements.add(moveTo)
        path.elements.add(linetTo)

        return path
    }

    private fun move(count: Int): PathTransition {
        val ptr = PathTransition()
        ptr.duration = Duration.millis(600.0)
        ptr.node = this
        ptr.path = path(count)
        ptr.cycleCount = 1
        ptr.play()
        return ptr
    }
}