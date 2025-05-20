package com.example.juegotetris.models

class Tetris(val cols: Int = 10, val rows: Int = 20) {

    private val board: Array<Array<Cell>> = Array(rows) {
        Array(cols) { Cell.EMPTY }
    }

    fun resetBoard() {
        for (y in 0 until rows) {
            for (x in 0 until cols) {
                board[y][x] = Cell.EMPTY
            }
        }
    }

    fun isCellOccupied(x: Int, y: Int): Boolean {
        return board[y][x] != Cell.EMPTY
    }

    fun setCell(x: Int, y: Int, cell: Cell) {
        board[y][x] = cell
    }

    fun getCell(x: Int, y: Int): Cell {
        return board[y][x]
    }

    fun clearFullRows(): Int {
        var linesCleared = 0
        for (y in board.indices.reversed()) {
            if (board[y].all { it != Cell.EMPTY }) {
                removeRow(y)
                linesCleared++
            }
        }
        return linesCleared
    }

    private fun removeRow(rowIndex: Int) {
        for (y in rowIndex downTo 1) {
            board[y] = board[y - 1].copyOf()
        }
        board[0] = Array(cols) { Cell.EMPTY }
    }
}