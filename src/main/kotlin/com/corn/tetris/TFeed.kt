package com.corn.tetris

import com.corn.tetris.shape.*
import kotlin.random.Random

class TFeed {

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
            0 -> TLine()
            1 -> TCube()
            2 -> TLform()
            3 -> TTform()
            else -> TCform()
        }
    }

}