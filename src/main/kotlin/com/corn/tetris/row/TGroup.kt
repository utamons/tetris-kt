package com.corn.tetris.row

import com.corn.tetris.CELL_G
import com.corn.tetris.COLS
import com.corn.tetris.GAP
import javafx.animation.PathTransition
import javafx.event.EventHandler
import javafx.scene.Group
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.scene.shape.LineTo
import javafx.scene.shape.MoveTo
import javafx.scene.shape.Path
import javafx.util.Duration

class TGroup(private val rows: List<TRow>) : Group() {

    init {
        children.addAll(rows)
        layoutX = rows[0].layoutX
        layoutY = rows[0].layoutY
    }

    private fun path(count: Int): Path {
        val path = Path()

        val moveTo = MoveTo(centerX(), centerY())
        val linetTo = LineTo(centerX(), centerY() + CELL_G * count)

        path.elements.add(moveTo)
        path.elements.add(linetTo)

        return path
    }

    private fun centerX() : Double {
        return CELL_G / 2 * COLS - GAP / 2
    }

    private fun centerY() : Double {
        return rows.size * CELL_G / 2
    }

    fun fall(count: Int, listener: () -> Unit): PathTransition {
        val ptr = PathTransition()
        ptr.duration = Duration.millis(600.0)
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