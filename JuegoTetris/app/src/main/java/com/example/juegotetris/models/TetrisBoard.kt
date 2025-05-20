package com.example.juegotetris.models

class TetrisBoard(val cols: Int = 10, val rows: Int = 20) {

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

    fun clearBoard() {
        for (y in 0 until rows) {
            for (x in 0 until cols) {
                board[y][x] = Cell.EMPTY
            }
        }
    }

    fun drawActivePiece(): List<Pair<Int, Int>> {
        return activePiece?.getOccupiedCells() ?: emptyList()
    }

    fun spawnPiece() {
        activePiece = Tetromino.random()
    }

    fun movePieceDown(): Boolean {
        val piece = activePiece ?: return false
        if (piece.canMoveTo(piece.x, piece.y + 1, this)) {
            piece.y += 1
            return true
        }
        return false
    }

    fun fixActivePieceToBoard() {
        val piece = activePiece ?: return
        for ((x, y) in piece.getOccupiedCells()) {
            if (y in 0 until rows && x in 0 until cols) {
                setCell(x, y, piece.cellType)
            }
        }
        activePiece = null
    }

    fun rotateActivePiece() {
        activePiece?.rotate(this)
    }


}