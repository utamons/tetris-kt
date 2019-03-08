package com.corn.tetris

import com.corn.tetris.shape.TShape
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
    private val feed = TFeed(startPoint(basePoint))
    private var currentShape: TShape
    private var nextShape: TShape
    private val startPoint = startPoint(basePoint)
    private var count = 1

    init {
        children.add(container)

        (0 until ROWS).forEach { i ->
            children.add(TRow(Point2D(startPoint.x, startPoint.y + i * (CELL_G))))
        }

        currentShape = feed.currentShape()
        nextShape = feed.nextShape()
        currentShape.layoutX = startPoint.x + (COLS / 2 - currentShape.hCells() / 2) * (CELL_G)
        nextShape.layoutX = startPoint.x + COLS * CELL_G + L_WIDTH*2 + 50
        children.add(currentShape)
        children.add(nextShape)

        onKeyPressed = EventHandler { event ->
            processKey(event)
        }

        /* val rotate = Rotate()
         rotate.angle = 90.0
         rotate.pivotX = shape.pivot().x
         rotate.pivotY = shape.pivot().y

         shape.transforms.add(rotate)*/
    }

    private fun processKey(event: KeyEvent) {
        when {
            (event.code == KeyCode.LEFT && canFit(currentShape.shapeLeft(count))) -> currentShape.moveLeft(count)
            (event.code == KeyCode.RIGHT && canFit(currentShape.shapeRight(count))) -> currentShape.moveRight(count)
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
        val ptr = currentShape.moveDown(count)
        ptr.onFinished = EventHandler {
            if (canFit(currentShape.shapeDown(count))) {
                count++
                play()
            } else {
                fix()
                count = 1
                currentShape = feed.currentShape()
                children.remove(nextShape)

                currentShape.layoutX = startPoint.x + (COLS / 2 - currentShape.hCells() / 2) * CELL_G
                currentShape.layoutY -= CELL_G * (currentShape.vCells() - 1)
                children.add(currentShape)
                if (canFit(currentShape.shapeDown(count-1))) {
                    play()
                    nextShape = feed.nextShape();
                    children.add(nextShape)
                    nextShape.layoutX = startPoint.x + COLS * CELL_G + L_WIDTH*2 + 50
                }  else {
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