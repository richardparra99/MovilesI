package com.example.juegotetris.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "puntaje")
data class Score(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val playerName: String,
    val score: Int
)
