package com.corn.tetris

import com.corn.tetris.shape.TShape
import javafx.animation.PathTransition
import javafx.event.EventHandler
import javafx.geometry.Point2D
import javafx.scene.Group
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent

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
        nextShape.translateY = startPoint.y + (CELL_G) / 2 * currentShape.vCells()
        nextShape.translateX = startPoint.x + COLS * CELL_G + L_WIDTH * 2 + 50
        children.add(currentShape)
        children.add(nextShape)

        onKeyPressed = EventHandler { event ->
            processKey(event)
        }
    }

    private fun processKey(event: KeyEvent) {
        when {
            (event.code == KeyCode.LEFT && canFit(currentShape.shapeLeft())) -> {
                trDown.stop()
                currentShape.updatePoint()
                val pt = currentShape.moveLeft()
                pt.onFinished = EventHandler {
                    currentShape.updatePoint()
                    play()
                }
            }
            (event.code == KeyCode.RIGHT && canFit(currentShape.shapeRight())) -> {
                trDown.stop()
                currentShape.updatePoint()
                val pt = currentShape.moveRight()
                pt.onFinished = EventHandler {
                    currentShape.updatePoint()
                    play()
                }
            }
        }
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
            if (canFit(currentShape.shapeDown())) {
                currentShape.updatePoint()
                currentShape.setNextY()
                play()
            } else {
                fix()
                currentShape = feed.currentShape()
                currentShape.startPoint(startPoint)
                if (canFit(currentShape.shapeDown())) {
                    children.remove(nextShape)

                    play()

                    children.add(currentShape)
                    nextShape = feed.nextShape()
                    children.add(nextShape)
                    nextShape.translateY = startPoint.y + (CELL_G) / 2 * currentShape.vCells()
                    nextShape.translateX = startPoint.x + COLS * CELL_G + L_WIDTH * 2 + 50
                } else {
                    fix()
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