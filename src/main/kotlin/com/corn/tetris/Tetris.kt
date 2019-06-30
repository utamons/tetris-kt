package com.corn.tetris

import com.corn.tetris.row.TRow
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
    private var pause = false
    private val rows = ArrayList<TRow>()

    init {
        children.add(container)

        (0 until ROWS).forEach { i ->
            val row = TRow(Point2D(startPoint.x, startPoint.y + i * (CELL_G)))
            children.add(row)
            rows.add(row)
            row.idx = i
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
            (event.code == KeyCode.SPACE) -> {
                if (pause)
                    trDown.play()
                else
                    trDown.stop()
                pause = !pause
            }
            (event.code == KeyCode.UP) -> {
                // do rotate
            }
            (event.code == KeyCode.DOWN) -> {
                currentShape.speed = 10.0
            }
            (event.code == KeyCode.LEFT) -> {
                // mode left
            }
            (event.code == KeyCode.RIGHT) -> {
                // move right
            }
        }
    }

    private fun canFit(): Boolean {
        val b = currentShape.boundsDown();
        return b.all { it.minY < rows[ROWS - 1].boundsInParent.maxY } && rows.all { it.canFit(b) }
    }

    private fun canFit(angle: Double): Boolean {
        throw NotImplementedError();
    }

    private fun fix() {
        rows.forEach {
            it.fix(currentShape.allBounds())
        }
    }


    fun play() {
        if (canFit()) {
            trDown = currentShape.moveDown()
            trDown.onFinished = EventHandler {
                currentShape.centerY = currentShape.centerY + CELL_G;
                currentShape.nextY = currentShape.centerY + CELL_G
                play()
            }
        } else {
            println("fix")
            fix()
            children.remove(currentShape)
            children.remove(nextShape)
            currentShape = feed.currentShape()

            currentShape.startPoint(startPoint)

            nextShape = feed.nextShape()
            nextShape.translateY = startPoint.y + (CELL_G) / 2 * currentShape.vCells()
            nextShape.translateX = startPoint.x + COLS * CELL_G + L_WIDTH * 2 + 50
            children.add(currentShape)
            children.add(nextShape)
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