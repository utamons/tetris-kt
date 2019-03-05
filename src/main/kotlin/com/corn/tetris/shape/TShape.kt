package com.corn.tetris.shape

import com.corn.tetris.CELL_G
import com.corn.tetris.CELL_SIZE
import com.corn.tetris.GAP
import javafx.animation.PathTransition
import javafx.geometry.Point2D
import javafx.scene.Group
import javafx.scene.paint.Color
import javafx.scene.shape.LineTo
import javafx.scene.shape.MoveTo
import javafx.scene.shape.Path
import javafx.scene.shape.Rectangle
import javafx.util.Duration

abstract class TShape(basePoint: Point2D) : Group() {

    private val color = Color.DARKGREEN

    init {
        layoutX = basePoint.x
        layoutY = basePoint.y
    }

    protected fun rect(x: Double, y: Double) {
        val rect = Rectangle(CELL_SIZE, CELL_SIZE)
        rect.arcHeight = 20.0
        rect.arcWidth = 20.0
        rect.fill = color
        //rect.stroke = color
        rect.x = x
        rect.y = y
        children.add(rect)
    }

    protected fun probeRect(x: Double, y: Double, shape: TShape) {
        val rect = Rectangle(CELL_SIZE, CELL_SIZE)
        rect.arcHeight = 20.0
        rect.arcWidth = 20.0
        rect.fill = Color.TRANSPARENT
       // rect.stroke = Color.INDIGO
        rect.x = x
        rect.y = y
        shape.children.add(rect)
    }

    /*fun pivot() : Point2D {
        val x = boundsInLocal.width/2.0
        val y = GAP / 2 + boundsInLocal.height/2.0
        return Point2D(x,y)
    }*/

    abstract fun hCells() : Int
    abstract fun vCells() : Int
    abstract fun probeTo(basepoint: Point2D): TShape

    fun shapeDown(count:Int) : TShape {
        val x = layoutX
        val y = layoutY + (CELL_G) * (count+1)


        return probeTo(Point2D(x,y))
    }

    private fun pathDown(count: Int): Path {
        val path = Path()

        val startPt = Point2D((CELL_G)/2 * hCells() - GAP/2, (CELL_G)/2 * vCells() - GAP/2 )
        val x = startPt.x
        val y = startPt.y + (CELL_G) * (count-1)  + GAP/2

        val moveTo = MoveTo(x, y)
        val linetTo = LineTo(x, y + CELL_G + GAP/2)

        path.elements.add(moveTo)
        path.elements.add(linetTo)

        return path
    }

    fun moveDown(count: Int) : PathTransition{
        val ptr = PathTransition()
        ptr.duration = Duration.millis(500.0)
        ptr.node = this
        ptr.path = pathDown(count)
        ptr.cycleCount = 1
        //ptr.isAutoReverse = true
        ptr.play()
        return ptr
    }


}