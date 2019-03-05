package com.corn.tetris

import com.corn.tetris.shape.TCube
import com.corn.tetris.shape.TLine
import com.corn.tetris.shape.TShape
import javafx.geometry.Point2D
import kotlin.random.Random

class TFeed(basePoint: Point2D) {

    private val pt= basePoint

    fun nextShape() : TShape {
        val idx = Random.nextInt(2)
        return if (idx == 0)
            TLine(Point2D(pt.x, pt.y))
        else
            TCube(Point2D(pt.x, pt.y))
    }
}