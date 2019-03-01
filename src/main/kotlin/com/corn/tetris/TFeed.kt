package com.corn.tetris

import com.corn.tetris.shape.TCube
import com.corn.tetris.shape.TLine
import com.corn.tetris.shape.TShape
import javafx.geometry.Point2D
import kotlin.random.Random

class TFeed(basePoint: Point2D) {

    val shapes = ArrayList<TShape>()

    init {
        shapes.add(TLine(5, CELL_SIZE, GAP, Point2D(basePoint.x, basePoint.y)))
        shapes.add(TCube(CELL_SIZE, GAP, Point2D(basePoint.x, basePoint.y)))
    }

    fun nextShape() : TShape {
        val idx = Random.nextInt(shapes.size)
        return shapes[idx]
    }
}