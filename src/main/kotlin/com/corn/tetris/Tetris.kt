package com.corn.tetris

import com.corn.tetris.shape.TShape
import javafx.geometry.Point2D
import javafx.scene.Group

const val CELL_SIZE = 45.0
const val GAP = 4.0
const val COLS = 9
const val ROWS = 19
const val L_WIDTH = 10.0

class Tetris(basePoint: Point2D) : Group() {

    private val container: TContainer = TContainer(COLS, ROWS, GAP, CELL_SIZE, L_WIDTH, basePoint)
    private val feed = TFeed(startPoint(basePoint))
    private var currentShape: TShape;

    init {
        children.add(container)

        val startPoint = startPoint(basePoint)

        for (i in (0 until ROWS)) {
            children.add(TRow(COLS, CELL_SIZE, GAP, Point2D(startPoint.x, startPoint.y + i * (CELL_SIZE + GAP))))
        }

        currentShape = feed.nextShape()
        currentShape.layoutX = startPoint.x + (COLS / 2 - currentShape.hCells() / 2) * (CELL_SIZE + GAP)
        children.addAll(currentShape)

        /* val rotate = Rotate()
         rotate.angle = 90.0
         rotate.pivotX = shape.pivot().x
         rotate.pivotY = shape.pivot().y

         shape.transforms.add(rotate)*/

        for (child in children) {
            if (child is TRow) {
                println(child.canFit(currentShape))
            }
        }
    }

    fun play() {
        currentShape.move()
    }

    private fun startPoint(basePoint: Point2D): Point2D {
        val xShift = L_WIDTH / 2 + GAP
        val yShift = L_WIDTH / 2 + GAP / 2
        val absX = basePoint.x + xShift
        val absY = basePoint.y + yShift
        return Point2D(absX, absY)
    }
}