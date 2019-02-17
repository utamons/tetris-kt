package com.corn.tetris

import javafx.animation.Animation.INDEFINITE
import javafx.animation.PathTransition
import javafx.application.Application
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


class TetrisApp : Application() {

    override fun start(primaryStage: Stage) {
        primaryStage.title = "KTetris"

        val rect = TShape()
        rect.layoutX = 200.0
        rect.layoutY = 200.0

        val rectNext = TShape()
        rectNext.layoutX = 650.0
        rectNext.layoutY = 100.0

        val banner = Text()
        banner.isCache = true
        banner.x = 150.0
        banner.y = 60.0
        banner.boundsType = TextBoundsType.LOGICAL_VERTICAL_CENTER
        banner.fill = Color.web("0x3b596d")
        banner.text = "Level 1, score 350"
        banner.font = Font.font(null, FontWeight.BOLD, 40.0)

        val record = Text()
        record.isCache = true
        record.x = 650.0
        record.y = 300.0
        record.boundsType = TextBoundsType.LOGICAL_VERTICAL_CENTER
        record.fill = Color.web("0x3b596d")
        record.text = "Record: 350"
        record.font = Font.font(null, FontWeight.BOLD, 30.0)

        val container = TContainer()

        val root = Group()
        root.children.addAll(container, rect, rectNext, banner, record)

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