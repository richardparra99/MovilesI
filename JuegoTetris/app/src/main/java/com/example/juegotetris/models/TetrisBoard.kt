package com.example.juegotetris.models

import com.example.juegotetris.Observer.Observable
import com.example.juegotetris.Observer.Observer

class TetrisBoard(val cols: Int = 10, val rows: Int = 20) : Observable{
    private var observer: Observer? = null

    val scoreManager = ScoreManager()

    private val board: Array<Array<Cell>> = Array(rows) {
        Array(cols) { Cell.EMPTY }
    }

    var activePiece: Tetromino? = null

    fun getCell(x: Int, y: Int): Cell {
        return board[y][x]
    }

    fun setCell(x: Int, y: Int, cell: Cell) {
        board[y][x] = cell
    }

    fun drawActivePiece(): List<Pair<Int, Int>> {
        return activePiece?.getOccupiedCells() ?: emptyList()
    }

    fun spawnPiece(): Boolean {
        val newPiece = Tetromino.random()
        if (!newPiece.canMoveTo(newPiece.x, newPiece.y, this)) {
            return false
        }
        activePiece = newPiece
        return true
    }


    fun movePieceDown(): Boolean {
        val piece = activePiece ?: return false
        if (piece.canMoveTo(piece.x, piece.y + 1, this)) {
            piece.y += 1
            notifyObservers()
            return true
        }
        return false
    }

    fun clearFullLines(): Int {
        var linesCleared = 0

        for (y in board.indices.reversed()) {
            if (board[y].all { it != Cell.EMPTY }) {
                removeRow(y)
                linesCleared++
            }
        }

        if (linesCleared > 0) {
            scoreManager.addClearedLines(linesCleared)
        }

        return linesCleared
    }

    private fun removeRow(rowIndex: Int) {
        for (y in rowIndex downTo 1) {
            board[y] = board[y - 1].copyOf()
        }
        board[0] = Array(cols) { Cell.EMPTY }
    }

    fun fixActivePieceToBoard() {
        val piece = activePiece ?: return
        for ((x, y) in piece.getOccupiedCells()) {
            if (y in 0 until rows && x in 0 until cols) {
                setCell(x, y, piece.cellType)
            }
        }

        activePiece = null

        clearFullLines()
        notifyObservers()
    }

    fun reset() {
        for (y in board.indices) {
            board[y].fill(Cell.EMPTY)
        }
        scoreManager.score = 0
        activePiece = null
        spawnPiece()
    }


    fun rotateActivePiece() {
        activePiece?.rotate(this)
        notifyObservers()
    }

    override fun addObserver(observer: Observer) {
        this.observer = observer
    }

    override fun notifyObservers() {
        observer?.update()
    }
}