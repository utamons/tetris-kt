package com.corn.tetris

import javafx.geometry.Point2D
import javafx.scene.Group
import javafx.scene.transform.Rotate

const val CELL_SIZE = 50.0
const val GAP = 4.0
const val COLS = 8
const val ROWS = 17
const val L_WIDTH = 10.0

class Tetris(basePoint: Point2D) : Group() {

    private val container: TContainer = TContainer(COLS, ROWS, GAP, CELL_SIZE, L_WIDTH, basePoint)

    init {
        children.add(container)

        val xShift = L_WIDTH / 2 + GAP
        val yShift = L_WIDTH / 2 + GAP / 2
        val absX = basePoint.x + xShift
        val absY = basePoint.y + yShift

        for (i in (0 until ROWS)) {
            children.add(TRow(COLS, CELL_SIZE, GAP, Point2D(absX, absY + i * (CELL_SIZE + GAP))))
        }

        val shape = TShape(CELL_SIZE, GAP, Point2D(absX, absY))
        children.addAll(shape)

        val rotate = Rotate()
        rotate.angle = 90.0
        rotate.pivotX = shape.pivot().x
        rotate.pivotY = shape.pivot().y

        shape.transforms.add(rotate)

        for (child in children) {
            if (child is TRow) {
                println(child.canFit(shape))
            }
        }
    }

}