package com.corn.tetris

import com.corn.tetris.row.Gap
import com.corn.tetris.row.TGroup
import com.corn.tetris.row.TRow
import com.corn.tetris.shape.TShape
import javafx.animation.PathTransition
import javafx.event.EventHandler
import javafx.geometry.Point2D
import javafx.scene.Group
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.util.Duration


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
    private var lock = false
    private var pause = false
    private val rows = ArrayList<TRow>()
    private val gaps = ArrayList<Gap>()

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
        if (lock) {
            println("Locked")
            return
        }

        when {
            (event.code == KeyCode.SPACE) -> {
                if (pause)
                    trDown.play()
                else
                    trDown.stop()
                pause = !pause
            }
            (event.code == KeyCode.UP && canFit(-90.0)) -> {
                lock = true
                val y = currentShape.translateY
                trDown.stop()
                val pt = currentShape.rotate(-90.0)
                pt.onFinished = EventHandler {
                    currentShape.translateY = y
                    currentShape.updatePoint()
                    play()
                    lock = false
                }
            }
            (event.code == KeyCode.DOWN && canFit(90.0)) -> {
                lock = true
                trDown.stop()
                currentShape.updatePoint()
                val pt = currentShape.rotate(90.0)
                pt.onFinished = EventHandler {
                    play()
                    lock = false
                }
            }
            (event.code == KeyCode.LEFT && canFit(currentShape.shapeLeft())) -> {
                lock = true
                trDown.stop()
                currentShape.updatePoint()
                val pt = currentShape.moveLeft()
                pt.onFinished = EventHandler {
                    currentShape.updatePoint()
                    play()
                    lock = false
                }
            }
            (event.code == KeyCode.RIGHT && canFit(currentShape.shapeRight())) -> {
                lock = true
                trDown.stop()
                currentShape.updatePoint()
                val pt = currentShape.moveRight()
                pt.onFinished = EventHandler {
                    currentShape.updatePoint()
                    play()
                    lock = false
                }
            }
        }
    }

    private fun canFit(probe: TShape): Boolean {
        return children.none { (it is TRow && !it.canFit(probe)) || (it is TContainer && !it.canFit(probe)) }
    }

    private fun canFit(angle: Double): Boolean {
        val delta = angle / 90.0
        var count = 0.0
        while (count != angle) {
            if (!canFit(currentShape.deltaRotate(count))) {
                return false
            }
            count += delta
        }
        return true
    }

    private fun fix() {
        rows.forEach { row ->
            row.fix(currentShape)
        }
        children.remove(currentShape)
        processFalling()
    }

    private fun processFalling() {
        updateGaps()
        var count = gaps.size
        gaps.forEach { gap ->
            gap.disappear {
                if (--count == 0) {
                    fall(0)
                }
            }
        }
    }

    private fun fall(i: Int) {
        if (i == gaps.size) {
            gaps.clear()
            println("Felt")
        } else {
            fall(gaps[i]) {
                children.removeAll(gaps[i].rows)
                rows.removeAll(gaps[i].rows)
                (0 until gaps[i].size).forEach { j ->
                    val row = TRow(Point2D(startPoint.x, startPoint.y + j * (CELL_G)))
                    rows.add(0, row)
                    updatePos()
                    children.add(row)
                    row.idx = j
                }
                fall(i + 1)
            }
        }
    }

    private fun fall(gap: Gap, listener: () -> Unit) {
        val group = TGroup(startPoint, rows.filter { it.idx < gap.min })
        children.add(group)
        group.fall(gap.size) {
            children.addAll(group.children)
            children.remove(group)
            listener()
        }
    }

    private fun updatePos() {
        rows.onEach { r ->
            r.layoutY = startPoint.y + r.idx * CELL_G
        }
    }

    private fun updateGaps() {
        rows.filter { it.isFull() && gaps.none { gap -> gap.rows.contains(it) } }.forEach { row ->
            if (gaps.none { gap -> gap.add(row) }) {
                val gap = Gap()
                gap.add(row)
                gaps.add(gap)
            }
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