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
        return when (Random.nextInt(6)) {
            0 -> TLine()
            1 -> TCform()
            2 -> TLform()
            3 -> TTform()
            4 -> TRform()
            else -> TCube()
        }
    }

}