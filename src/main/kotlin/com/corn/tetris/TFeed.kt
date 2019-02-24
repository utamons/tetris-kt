package com.corn.tetris

import com.corn.tetris.shape.TLine
import com.corn.tetris.shape.TShape
import javafx.geometry.Point2D
import kotlin.random.Random

class TFeed(basePoint: Point2D) {

    private val shapes = ArrayList<TShape>()

    init {
        shapes.add(TLine(CELL_SIZE, GAP, Point2D(basePoint.x, basePoint.y)))
    }

    fun nextShape() : TShape {
        val idx = Random.nextInt(shapes.size)
        return shapes.get(idx)
    }
}