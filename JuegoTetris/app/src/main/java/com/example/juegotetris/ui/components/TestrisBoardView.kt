package com.example.juegotetris.ui.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.juegotetris.models.Cell
import com.example.juegotetris.models.TetrisBoard
import android.os.Handler
import com.example.juegotetris.Observer.Observer


class TestrisBoardView(context: Context?, attrs: AttributeSet?) : View(context, attrs), Observer {
    private val board = TetrisBoard()
    private val paint = Paint()
    private val handler = Handler()
    //private val gravitySpeed = 500L // cada 0.5 segundos

    fun updateScoreCallback(callback: (Int, Int) -> Unit) {
        scoreUpdateCallback = callback
    }

    private var scoreUpdateCallback: ((Int, Int) -> Unit)? = null

    private var gameOverCallback: (() -> Unit)? = null

    private val gravityRunnable = object : Runnable {
        override fun run() {
            val moved = board.movePieceDown()
            if (!moved) {
                board.fixActivePieceToBoard()
                val success = board.spawnPiece()

                if (!success) {
                    stopGame()
                    gameOverCallback?.invoke()
                    return
                }

                scoreUpdateCallback?.invoke(
                    board.scoreManager.score,
                    board.scoreManager.level
                )
            }

            handler.postDelayed(this, board.scoreManager.getGravityDelay())
        }
    }



    init {
        board.addObserver(this)
        board.spawnPiece()
        handler.postDelayed(gravityRunnable, board.scoreManager.getGravityDelay())
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val cellWidth = width / board.cols
        val cellHeight = height / board.rows

        for (y in 0 until board.rows) {
            for (x in 0 until board.cols) {
                drawCell(canvas, x, y, board.getCell(x, y), cellWidth, cellHeight)
            }
        }

        val pieceCells = board.drawActivePiece()
        for ((x, y) in pieceCells) {
            drawCell(canvas, x, y, board.activePiece?.cellType ?: Cell.FILLED_RED, cellWidth, cellHeight)
        }
    }

    private fun drawCell(canvas: Canvas, x: Int, y: Int, cell: Cell, cw: Int, ch: Int) {
        paint.color = when (cell) {
            Cell.EMPTY -> Color.LTGRAY
            Cell.FILLED_RED -> Color.RED
            Cell.FILLED_GREEN -> Color.GREEN
            Cell.FILLED_BLUE -> Color.BLUE
            Cell.FILLED_YELLOW -> Color.YELLOW
            Cell.FILLED_PURPLE -> Color.MAGENTA
        }

        canvas.drawRect(
            (x * cw).toFloat(),
            (y * ch).toFloat(),
            ((x + 1) * cw).toFloat(),
            ((y + 1) * ch).toFloat(),
            paint
        )
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            val touchedX = event.x

            if (touchedX < width / 2) {
                movePieceLeft()
            } else {
                movePieceRight()
            }

            return true
        }

        return super.onTouchEvent(event)
    }

    private fun movePieceLeft() {
        board.activePiece?.moveLeft(board)
    }

    private fun movePieceRight() {
        board.activePiece?.moveRight(board)
    }

    fun dropToBottom() {
        while (board.movePieceDown()) {
        }
        board.fixActivePieceToBoard()
        board.spawnPiece()
    }

    fun stopGame() {
        handler.removeCallbacks(gravityRunnable)
    }

    fun onGameOver(callback: () -> Unit) {
        gameOverCallback = callback
    }

    fun resetGame() {
        board.reset()
        handler.postDelayed(gravityRunnable, board.scoreManager.getGravityDelay())
        scoreUpdateCallback?.invoke(0, 1)
    }

    fun getFinalScore(): Int {
        return board.scoreManager.score
    }


    fun rotateCurrentPiece() {
        board.rotateActivePiece()
    }

    override fun update() {
        invalidate()
    }

}