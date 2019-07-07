package com.corn.tetris

import com.corn.tetris.row.Gap
import com.corn.tetris.row.TGroup
import com.corn.tetris.row.TRow
import com.corn.tetris.shape.TShape
import javafx.animation.PathTransition
import javafx.animation.Timeline
import javafx.event.EventHandler
import javafx.geometry.Bounds
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
    private var tr: PathTransition = PathTransition()
    private var tl: Timeline = Timeline()
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
            (event.code == KeyCode.P) -> {
                if (pause)
                    tr.play()
                else
                    tr.stop()
                pause = !pause
            }
            (event.code == KeyCode.UP && canFit(currentShape.angle)) -> {
                tr.stop()
                currentShape.updatePoint()
                tl = currentShape.rotate(90.0)
                tl.onFinished = EventHandler {
                    currentShape.updatePoint()
                    tr = currentShape.moveDown();
                    tr.onFinished = EventHandler {
                        currentShape.updatePoint()
                        currentShape.steps++
                        play()
                    }
                }
            }
            (event.code == KeyCode.SPACE) -> {
                currentShape.speed = 10.0
            }
            (event.code == KeyCode.LEFT && canFit(currentShape.boundsLeft())) -> {
                tr.stop()
                currentShape.updatePoint()
                tr = currentShape.moveLeft()
                tr.onFinished = EventHandler {
                    currentShape.updatePoint()
                    tr = currentShape.moveDown();
                    tr.onFinished = EventHandler {
                        currentShape.updatePoint()
                        currentShape.steps++
                        play()
                    }
                }
            }
            (event.code == KeyCode.RIGHT && canFit(currentShape.boundsRight())) -> {
                tr.stop()
                currentShape.updatePoint()
                tr = currentShape.moveRight()
                tr.onFinished = EventHandler {
                    currentShape.updatePoint()
                    tr = currentShape.moveDown();
                    tr.onFinished = EventHandler {
                        currentShape.updatePoint()
                        currentShape.steps++
                        play()
                    }
                }
            }
        }
    }

    private fun canFit(b: List<Bounds>): Boolean {
        return b.all { it.minY < rows[ROWS - 1].boundsInParent.maxY } &&
                b.all { it.minX >= rows[0].boundsInParent.minX } &&
                b.all { it.maxX <= rows[0].boundsInParent.maxX } &&
                rows.all { it.canFit(b) }
    }

    private fun canFit(angle: Double): Boolean {
        var i = currentShape.angle;
        while (i < currentShape.angle + angle) {
            if (!canFit(currentShape.boundsAngle(i)))
                return false
            i += 1.0
        }

        return true;
    }

    private fun fix() {
        rows.forEach {
            it.fix(currentShape.allBounds())
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
        if (canFit(currentShape.boundsDown())) {
            tr = currentShape.moveDown()
            tr.onFinished = EventHandler {
                currentShape.centerY = currentShape.centerY + CELL_G;
                currentShape.steps++
                play()
            }
        } else {
            println("fix")
            fix()
            children.remove(nextShape)
            currentShape = feed.currentShape()

            currentShape.startPoint(startPoint)

            nextShape = feed.nextShape()
            nextShape.translateY = startPoint.y + (CELL_G) / 2 * currentShape.vCells()
            nextShape.translateX = startPoint.x + COLS * CELL_G + L_WIDTH * 2 + 50
            if (canFit(currentShape.boundsDown())) {
                children.add(currentShape)
                children.add(nextShape)
                play()
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