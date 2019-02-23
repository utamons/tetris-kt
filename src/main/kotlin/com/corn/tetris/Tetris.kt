package com.corn.tetris

import javafx.geometry.Point2D
import javafx.scene.Group
import javafx.scene.transform.Rotate

const val CELL_SIZE = 50.0
const val GAP = 4.0
const val COLS = 8
const val ROWS = 17
const val L_WIDTH = 10.0

class Tetris : Group() {

    private val container: TContainer = TContainer(COLS, ROWS, GAP, CELL_SIZE, L_WIDTH, Point2D(70.0, 80.0))

    init {
        children.add(container)
        for (i in (0..16)) {
            children.add(TRow(COLS, CELL_SIZE, GAP, Point2D(70.0 + (L_WIDTH / 2 + GAP), 80.0 + (L_WIDTH / 2 + GAP / 2) + i * (CELL_SIZE + GAP))))
        }

        val shape = TShape(CELL_SIZE, GAP, Point2D(70.0 + (L_WIDTH / 2 + GAP), 80.0 + (L_WIDTH / 2 + GAP / 2)))
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