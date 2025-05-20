package com.example.juegotetris.models

data class Tetromino(
    var x: Int = 3,
    var y: Int = 0,
    var shape: Array<IntArray>,
    val cellType: Cell
) {
    fun moveLeft(board: TetrisBoard): Boolean {
        if (canMoveTo(x - 1, y, board)) {
            x -= 1
            return true
        }
        return false
    }

    fun moveRight(board: TetrisBoard): Boolean {
        if (canMoveTo(x + 1, y, board)) {
            x += 1
            return true
        }
        return false
    }

    fun canMoveTo(newX: Int, newY: Int, board: TetrisBoard): Boolean {
        for (row in shape.indices) {
            for (col in shape[row].indices) {
                if (shape[row][col] != 0) {
                    val targetX = newX + col
                    val targetY = newY + row

                    if (targetX !in 0 until board.cols || targetY >= board.rows) return false
                    if (targetY >= 0 && board.getCell(targetX, targetY) != Cell.EMPTY) return false
                }
            }
        }
        return true
    }

    fun getOccupiedCells(): List<Pair<Int, Int>> {
        val cells = mutableListOf<Pair<Int, Int>>()
        for (row in shape.indices) {
            for (col in shape[row].indices) {
                if (shape[row][col] != 0) {
                    cells.add(Pair(x + col, y + row))
                }
            }
        }
        return cells
    }

    companion object {
        fun random(): Tetromino {
            val pieces = listOf(
                // Cuadrado O
                arrayOf(
                    intArrayOf(1, 1),
                    intArrayOf(1, 1)
                ),
                // Línea I
                arrayOf(
                    intArrayOf(1),
                    intArrayOf(1),
                    intArrayOf(1),
                    intArrayOf(1)
                ),
                // L
                arrayOf(
                    intArrayOf(1, 0),
                    intArrayOf(1, 0),
                    intArrayOf(1, 1)
                ),
                // J
                arrayOf(
                    intArrayOf(0, 1),
                    intArrayOf(0, 1),
                    intArrayOf(1, 1)
                ),
                // T
                arrayOf(
                    intArrayOf(1, 1, 1),
                    intArrayOf(0, 1, 0)
                ),
                // S
                arrayOf(
                    intArrayOf(0, 1, 1),
                    intArrayOf(1, 1, 0)
                ),
                // Z
                arrayOf(
                    intArrayOf(1, 1, 0),
                    intArrayOf(0, 1, 1)
                )
            )

            val colors = listOf(
                Cell.FILLED_RED,
                Cell.FILLED_GREEN,
                Cell.FILLED_BLUE,
                Cell.FILLED_YELLOW,
                Cell.FILLED_PURPLE
            )

            val shape = pieces.random()
            val color = colors.random()

            return Tetromino(
                x = 3,
                y = 0,
                shape = shape,
                cellType = color
            )
        }
    }

    fun rotate(board: TetrisBoard): Boolean {
        val newShape = rotateMatrix(shape)

        // Verificar si la nueva forma cabe en la posición actual
        for (row in newShape.indices) {
            for (col in newShape[row].indices) {
                if (newShape[row][col] != 0) {
                    val newX = x + col
                    val newY = y + row

                    if (newX !in 0 until board.cols || newY >= board.rows) {
                        return false // fuera de bordes
                    }

                    if (newY >= 0 && board.getCell(newX, newY) != Cell.EMPTY) {
                        return false // colisión
                    }
                }
            }
        }
        // Si no hay problema, aplicar rotación
        shape = newShape
        return true
    }

    private fun rotateMatrix(matrix: Array<IntArray>): Array<IntArray> {
        val rows = matrix.size
        val cols = matrix[0].size
        val rotated = Array(cols) { IntArray(rows) }

        for (row in matrix.indices) {
            for (col in matrix[row].indices) {
                rotated[col][rows - 1 - row] = matrix[row][col]
            }
        }
        return rotated
    }


}
