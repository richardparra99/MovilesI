package com.example.practicaroom.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.practicaroom.db.dao.RecetaDao
import com.example.practicaroom.db.models.Receta

@Database(entities = [Receta::class], version = 1)

abstract class AppDataBase : RoomDatabase() {
    abstract fun recetaDao(): RecetaDao

    companion object {
        fun getInstance(context: Context): AppDataBase{
            return Room.databaseBuilder(
                context,
                AppDataBase::class.java, "prueba-db"
            ).build()
        }
    }
}