package com.example.juegotetris.ui.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.example.juegotetris.models.Cell
import com.example.juegotetris.models.Tetris

class TestrisBoard(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val board = Tetris()
    private val paint = Paint()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val cellWidth = width / board.cols
        val cellHeight = height / board.rows

        for (y in 0 until board.rows) {
            for (x in 0 until board.cols) {
                when (board.getCell(x, y)) {
                    Cell.EMPTY -> paint.color = Color.LTGRAY
                    Cell.FILLED_RED -> paint.color = Color.RED
                    Cell.FILLED_GREEN -> paint.color = Color.GREEN
                    Cell.FILLED_BLUE -> paint.color = Color.BLUE
                    Cell.FILLED_YELLOW -> paint.color = Color.YELLOW
                    Cell.FILLED_PURPLE -> paint.color = Color.MAGENTA
                }

                canvas.drawRect(
                    (x * cellWidth).toFloat(),
                    (y * cellHeight).toFloat(),
                    ((x + 1) * cellWidth).toFloat(),
                    ((y + 1) * cellHeight).toFloat(),
                    paint
                )
            }
        }
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
        // TODO: lógica real más adelante
        Log.d("Tetris", "Tocar izquierda → mover a la izquierda")
    }

    private fun movePieceRight() {
        // TODO: lógica real más adelante
        Log.d("Tetris", "Tocar derecha → mover a la derecha")
    }
    // Podés agregar funciones para testear pintado manual
    fun testFillBoard() {
        board.setCell(0, 0, Cell.FILLED_RED)
        board.setCell(1, 0, Cell.FILLED_GREEN)
        board.setCell(2, 0, Cell.FILLED_BLUE)
        invalidate() // fuerza redibujado
    }
}