package com.example.juegotetris.db.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.juegotetris.db.Score

@Dao
interface ScoreDao {
    @Insert
    suspend fun insertScore(score: Score)

}