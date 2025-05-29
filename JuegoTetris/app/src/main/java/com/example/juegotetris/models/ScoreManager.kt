package com.example.juegotetris.models

class ScoreManager {
    var score: Int = 0
    var level: Int = 1
    private var linesClearedTotal = 0

    fun addClearedLines(lines: Int) {
        if (lines <= 0) return

        val basePoints = 10
        val points = if (lines >= 2) {
            lines * lines * basePoints
        } else {
            basePoints
        }

        score += points
        linesClearedTotal += lines
        updateLevel()
    }

    private fun updateLevel() {
        level = 1 + (linesClearedTotal / 5)
    }

    fun getGravityDelay(): Long {
        val baseDelay = 500L
        val reduction = (level - 1) * 40L
        return (baseDelay - reduction).coerceAtLeast(100L)
    }
}