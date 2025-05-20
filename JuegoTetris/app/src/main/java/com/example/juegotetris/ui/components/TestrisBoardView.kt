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


class TestrisBoardView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val board = TetrisBoard()
    private val paint = Paint()
    private val handler = Handler()
    private val gravitySpeed = 500L // cada 0.5 segundos

    private val gravityRunnable = object : Runnable {
        override fun run() {
            val moved = board.movePieceDown()
            if (moved) {
                invalidate()
            } else {
                // PIEZA YA NO PUEDE BAJAR
                board.fixActivePieceToBoard()  // Te la doy en el paso siguiente si querés
                board.spawnPiece()
            }
            handler.postDelayed(this, gravitySpeed) // 🔁 repetir siempre
        }
    }

    init {
        board.spawnPiece()
        handler.postDelayed(gravityRunnable, gravitySpeed)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val cellWidth = width / board.cols
        val cellHeight = height / board.rows

        // 1. Pintar tablero base
        for (y in 0 until board.rows) {
            for (x in 0 until board.cols) {
                drawCell(canvas, x, y, board.getCell(x, y), cellWidth, cellHeight)
            }
        }

        // 2. Pintar pieza activa
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
        invalidate()
    }

    private fun movePieceRight() {
        board.activePiece?.moveRight(board)
        invalidate()
    }

//    private val gravityRunnable = object : Runnable {
//        override fun run() {
//            val moved = board.movePieceDown()
//            if (moved) {
//                invalidate()
//                handler.postDelayed(this, gravitySpeed)
//            } else {
//                // Pieza no pudo moverse → se quedó fija (futuro)
//                Log.d("Tetris", "Pieza llegó al fondo (futuro: fijar pieza y nueva)")
//            }
//        }
//    }

    fun dropToBottom() {
        while (board.movePieceDown()) {
            // Sigue bajando hasta que ya no pueda
        }
        board.fixActivePieceToBoard()
        board.spawnPiece()
        invalidate()
    }

    fun rotateCurrentPiece() {
        board.rotateActivePiece()
        invalidate()
    }


}