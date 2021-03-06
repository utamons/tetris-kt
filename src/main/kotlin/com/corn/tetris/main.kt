package com.corn.tetris

import javafx.application.Application
import javafx.geometry.Point2D
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.paint.Paint
import javafx.stage.Stage
import java.awt.Color

class TetrisApp : Application() {

    override fun start(primaryStage: Stage) {
        primaryStage.title = "KTetris"

        val root = Group()
        val tetris = Tetris(Point2D(70.0, 80.0))
        tetris.play()
        root.children.addAll(tetris)

        primaryStage.scene = Scene(root, 900.0, 1100.0, Paint.valueOf("White"))
        primaryStage.show()
        tetris.requestFocus()
    }
}

fun main() {
    Application.launch(TetrisApp::class.java)
}

fun getHelloString() : String {
    return "Hello, tetris!"
}