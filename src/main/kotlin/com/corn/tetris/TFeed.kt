package com.corn.tetris

import com.corn.tetris.shape.TCube
import com.corn.tetris.shape.TLine
import com.corn.tetris.shape.TShape
import javafx.geometry.Point2D
import kotlin.random.Random

class TFeed(basePoint: Point2D) {

    val pt= basePoint


    fun nextShape() : TShape {
        val idx = Random.nextInt(2)
        if (idx == 0)
            return TLine(5, CELL_SIZE, GAP, Point2D(pt.x, pt.y))
        else
            return TCube(CELL_SIZE, GAP, Point2D(pt.x, pt.y))
    }
}