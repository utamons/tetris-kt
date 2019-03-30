package com.corn.tetris.row

import com.corn.tetris.CELL_G
import com.corn.tetris.COLS
import com.corn.tetris.GAP
import javafx.animation.PathTransition
import javafx.event.EventHandler
import javafx.geometry.Point2D
import javafx.scene.Group
import javafx.scene.shape.LineTo
import javafx.scene.shape.MoveTo
import javafx.scene.shape.Path
import javafx.util.Duration

class TGroup(val basePoint: Point2D, private val rows: List<TRow>) : Group() {

    init {
        children.addAll(rows)
        //rows.forEach{row-> row.children.forEach { (it as Rectangle).fill = Color.RED }}
    }

    private fun path(count: Int): Path {
        val path = Path()

        val moveTo = MoveTo(centerX(), centerY())
        val linetTo = LineTo(centerX(), centerY() + CELL_G * count)

        path.elements.add(moveTo)
        path.elements.add(linetTo)

        return path
    }

    private fun centerX(): Double {
        return basePoint.x + CELL_G / 2 * COLS - GAP / 2
    }

    private fun centerY(): Double {
        return basePoint.y + rows.size * CELL_G / 2
    }

    fun fall(count: Int, listener: () -> Unit): PathTransition {
        val ptr = PathTransition()
        ptr.duration = Duration.millis(200.0)
        ptr.node = this
        ptr.path = path(count)
        ptr.cycleCount = 1
        ptr.play()

        ptr.onFinished = EventHandler {
            rows.onEach {
                it.idx += count
            }
            listener()
        }

        return ptr
    }
}