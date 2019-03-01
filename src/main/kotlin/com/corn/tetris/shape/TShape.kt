package com.corn.tetris.shape

import com.corn.tetris.GAP
import com.corn.tetris.L_WIDTH
import com.corn.tetris.ROWS
import javafx.animation.Animation.INDEFINITE
import javafx.animation.PathTransition
import javafx.event.EventHandler
import javafx.geometry.Point2D
import javafx.scene.Group
import javafx.scene.paint.Color
import javafx.scene.shape.*
import javafx.util.Duration

abstract class TShape(private val cellSize: Double, private val gap: Double, basePoint: Point2D) : Group() {

    private val color = Color.DARKGREEN
   // private val path = pathDown(basePoint);

    init {
        layoutX = basePoint.x
        layoutY = basePoint.y
    }

    protected fun rect(x: Double, y: Double) {
        val rect = Rectangle(cellSize, cellSize)
        rect.arcHeight = 20.0
        rect.arcWidth = 20.0
        rect.fill = color
        //rect.stroke = color
        rect.x = x
        rect.y = y
        children.add(rect)
    }

    fun pivot() : Point2D {
        val x = boundsInLocal.width/2.0
        val y = gap / 2 + boundsInLocal.height/2.0
        return Point2D(x,y)
    }

    abstract fun hCells() : Int
    abstract fun vCells() : Int

    private fun pathDown(count: Int): Path {
        val path = Path()
        println(hCells())
        val startPt = Point2D((cellSize + gap)/2 * hCells() - gap/2, (cellSize + gap)/2 * vCells() - gap/2 )
        val x = startPt.x
        val y = startPt.y + (cellSize + gap) * (count-1)  + gap/2

        val moveTo = MoveTo(x, y)
        val linetTo = LineTo(x, y + cellSize + gap + gap/2)

        path.elements.add(moveTo)
        path.elements.add(linetTo)

        return path
    }

    fun move() {
        var count = 1;
        val ptr = PathTransition()
        ptr.duration = Duration.millis(500.0)
        ptr.node = this
        ptr.path = pathDown(count)
        ptr.cycleCount = 1
        //ptr.isAutoReverse = true
        ptr.onFinished = EventHandler {
            if (count < ROWS-vCells()) {
                count++
                ptr.path = pathDown(count)
                ptr.play()
            }
        }
        ptr.play()
    }
}