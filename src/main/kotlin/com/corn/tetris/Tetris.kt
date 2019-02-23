package com.corn.tetris

class Tetris {

    private val CELL_SIZE = 50.0
    private val GAP = 3.0
    private val COLS = 10
    private val ROWS = 18

    val container: TContainer = TContainer(COLS,ROWS,GAP,CELL_SIZE)
}