package com.example.practicaroom.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Receta(
    var titulo: String,
    var ingredientes: String,
    var preparacion: String
): Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}