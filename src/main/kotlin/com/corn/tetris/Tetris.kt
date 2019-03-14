package com.corn.tetris

import com.corn.tetris.shape.TShape
import javafx.animation.PathTransition
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
    private val feed = TFeed()
    private var currentShape: TShape
    private var nextShape: TShape
    private val startPoint = startPoint(basePoint)
    private var trDown: PathTransition = PathTransition()

    init {
        children.add(container)

        (0 until ROWS).forEach { i ->
            children.add(TRow(Point2D(startPoint.x, startPoint.y + i * (CELL_G))))
        }

        currentShape = feed.currentShape()

        currentShape.startPoint(startPoint)

        nextShape = feed.nextShape()
        nextShape.layoutX = startPoint.x + COLS * CELL_G + L_WIDTH * 2 + 50
        nextShape.layoutY = startPoint.y +  (CELL_G) / 2 * currentShape.vCells()
        children.add(currentShape)
        children.add(nextShape)
    }

    private fun canFit(probe: TShape): Boolean {
        return children.none { (it is TRow && !it.canFit(probe)) || (it is TContainer && !it.canFit(probe)) }
    }

    private fun fix() {
        children.filtered { it is TRow }.forEach { child ->
            (child as TRow).fix(currentShape)
        }
    }

    fun play() {
        trDown = currentShape.moveDown()
        trDown.onFinished = EventHandler {
            // y = (CELL_G) / 2 * currentShape.vCells() + currentShape.translateY
            if (canFit(currentShape.shapeDown())) {
                children.add(currentShape.shapeDown())
                currentShape.updatePoint()
                play()
            } else {
                fix()
                children.add(currentShape.shapeDown())
                currentShape = feed.currentShape()
                children.remove(nextShape)

                currentShape.startPoint(startPoint)

                children.add(currentShape)
                /* if (canFit(currentShape.shapeDown(x, y - CELL_G))) {
                    play()
                    nextShape = feed.nextShape();
                    children.add(nextShape)
                    nextShape.layoutX = startPoint.x + COLS * CELL_G + L_WIDTH * 2 + 50
                } else {
                    fix()
                }
            }*/
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