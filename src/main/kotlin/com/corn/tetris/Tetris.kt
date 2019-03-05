package com.corn.tetris

import com.corn.tetris.shape.TShape
import javafx.event.EventHandler
import javafx.geometry.Point2D
import javafx.scene.Group

const val CELL_SIZE = 45.0
const val GAP = 4.0
const val COLS = 9
const val ROWS = 19
const val L_WIDTH = 10.0
const val CELL_G = CELL_SIZE + GAP

class Tetris(basePoint: Point2D) : Group() {

    private val container: TContainer = TContainer(basePoint)
    private val feed = TFeed(startPoint(basePoint))
    private var currentShape: TShape
    private val startPoint = startPoint(basePoint)
    private var count = 1

    init {
        children.add(container)

        for (i in (0 until ROWS)) {
            children.add(TRow(Point2D(startPoint.x, startPoint.y + i * (CELL_G))))
        }

        currentShape = feed.nextShape()
        currentShape.layoutX = startPoint.x + (COLS / 2 - currentShape.hCells() / 2) * (CELL_G)
        children.addAll(currentShape)

        /* val rotate = Rotate()
         rotate.angle = 90.0
         rotate.pivotX = shape.pivot().x
         rotate.pivotY = shape.pivot().y

         shape.transforms.add(rotate)*/

        /* for (child in children) {
             if (child is TRow) {
                 println(child.canFit(currentShape))
             }
         }*/
    }

    private fun canFit(count: Int): Boolean {
        var result = true
        val sd = currentShape.shapeDown(count)
        for (child in children) {
            if (child is TRow) {
                result = result && child.canFit(sd)
            }
        }
        return result
    }

    private fun fix() {
        for (child in children) {
            if (child is TRow) {
                child.fix(currentShape)
            }
        }
    }

    fun play() {
        val ptr = currentShape.moveDown(count)
        ptr.onFinished = EventHandler {
            if (count < ROWS - currentShape.vCells() && canFit(count)) {
                count++
                play()
            } else {
                fix()
                count = 1
                currentShape = feed.nextShape()
                currentShape.layoutX = startPoint.x + (COLS / 2 - currentShape.hCells() / 2) * (CELL_G)
                if (canFit(count-1)) {
                    children.add(currentShape)
                    play()
                }
            }
        }
    }


    private fun startPoint(basePoint: Point2D): Point2D {
        val xShift = L_WIDTH / 2 + GAP
        val yShift = L_WIDTH / 2 + GAP / 2
        val absX = basePoint.x + xShift
        val absY = basePoint.y + yShift
        return Point2D(absX, absY)
    }
}