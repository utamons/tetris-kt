package com.corn.tetris

import com.corn.tetris.shape.*
import javafx.geometry.Point2D
import kotlin.random.Random

class TFeed(basePoint: Point2D) {

    private val pt = basePoint
    private var nextShape: TShape = getShape()

    fun currentShape(): TShape {
        val currentShape = nextShape
        nextShape = getShape()
        return currentShape
    }

    fun nextShape(): TShape {
        return nextShape
    }

    private fun getShape(): TShape {
        val idx = Random.nextInt(4)
        return when (idx) {
            0 -> TLine(Point2D(pt.x, pt.y))
            1 -> TCube(Point2D(pt.x, pt.y))
            2 -> TLform(Point2D(pt.x, pt.y))
            else -> TCform(Point2D(pt.x, pt.y))
        }
    }
}