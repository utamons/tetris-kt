package com.corn.tetris

import javafx.animation.Animation.INDEFINITE
import javafx.animation.PathTransition
import javafx.application.Application
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.effect.DropShadow
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.scene.shape.LineTo
import javafx.scene.shape.MoveTo
import javafx.scene.shape.Path
import javafx.scene.text.Font
import javafx.stage.Stage
import javafx.util.Duration
import javafx.scene.text.FontWeight
import javafx.scene.text.Text
import javafx.scene.text.TextBoundsType


class JFXApp : Application() {

    override fun start(primaryStage: Stage) {
        primaryStage.title = "Animation Test"

        val circle1 = circle(Color.BROWN)
        val circle3 = circle(Color.GREEN)
        val circle2 = circle(Color.GOLD)

        val dropShadow = DropShadow()
        dropShadow.radius = 15.0
        dropShadow.offsetX = 12.0
        dropShadow.offsetY = 12.0
        dropShadow.color = Color.color(0.4, 0.5, 0.5)

        circle1.effect = dropShadow
        circle2.effect = dropShadow
        circle3.effect = dropShadow

        val text = Text()
        text.effect = dropShadow
        text.isCache = true
        text.x = 90.0
        text.y = 50.0
        text.boundsType = TextBoundsType.LOGICAL_VERTICAL_CENTER
        text.fill = Color.web("0x3b596d")
        text.text = "Ета светафор."
        text.font = Font.font(null, FontWeight.BOLD, 40.0)


        val path1 = path(0.0)
        val path2 = path(70.0)
        val path3 = path(140.0)

        pathTransition(circle1, path1)
        pathTransition(circle2, path2)
        pathTransition(circle3, path3)

        val root = Group()
        root.children.add(text)
        root.children.add(circle1)
        root.children.add(circle2)
        root.children.add(circle3)

        primaryStage.scene = Scene(root, 480.0, 480.0)
        primaryStage.show()
    }

    private fun path(step: Double): Path {
        val path1 = Path()
        path1.elements.add(MoveTo(40.0 + step, 100.0 + step))
        path1.elements.add(LineTo(440.0 - step, 100.0 + step))
        path1.elements.add(LineTo(440.0 - step, 440.0 - step))
        path1.elements.add(LineTo(40.0 + step, 440.0 - step))
        path1.elements.add(LineTo(40.0 + step, 100.0 + step))
        return path1
    }

    private fun pathTransition(circle1: Circle, path1: Path) {
        val ptr = PathTransition()
        ptr.duration = Duration.millis(6000.0)
        ptr.node = circle1
        ptr.path = path1
        ptr.cycleCount = INDEFINITE
        ptr.isAutoReverse = true
        ptr.play()
    }

    private fun circle(color: Color): Circle {
        val circle = Circle()
        circle.centerX = 150.0
        circle.centerY = 135.0
        circle.radius = 30.0
        circle.fill = color
        circle.strokeWidth = 20.0
        return circle
    }

}

fun main() {
    Application.launch(JFXApp::class.java)
}

fun getHelloString() : String {
    return "Hello, tetris!"
}