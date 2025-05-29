package com.example.juegotetris.db.appDataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.juegotetris.db.Score
import com.example.juegotetris.db.dao.ScoreDao

@Database(entities = [Score::class], version = 1)
abstract class TetrisDataBase: RoomDatabase() {
    abstract fun scoreDao(): ScoreDao

    companion object {
        @Volatile private var INSTANCE: TetrisDataBase? = null
        fun getInstance(context: Context): TetrisDataBase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    TetrisDataBase::class.java,
                    "tetris_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}