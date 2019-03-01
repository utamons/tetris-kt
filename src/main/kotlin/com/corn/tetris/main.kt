package com.corn.tetris

import javafx.animation.Animation.INDEFINITE
import javafx.animation.PathTransition
import javafx.application.Application
import javafx.geometry.Point2D
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.effect.DropShadow
import javafx.scene.paint.Color
import javafx.scene.shape.*
import javafx.scene.text.Font
import javafx.stage.Stage
import javafx.util.Duration
import javafx.scene.text.FontWeight
import javafx.scene.text.Text
import javafx.scene.text.TextBoundsType
import javafx.scene.transform.Rotate


class TetrisApp : Application() {

    override fun start(primaryStage: Stage) {
        primaryStage.title = "KTetris"

        val root = Group()
        val tetris = Tetris(Point2D(70.0, 80.0))
        tetris.play()
        root.children.addAll(tetris)

        primaryStage.scene = Scene(root, 900.0, 1100.0)
        primaryStage.show()


    }
}

fun main() {
    Application.launch(TetrisApp::class.java)
}

fun getHelloString() : String {
    return "Hello, tetris!"
}