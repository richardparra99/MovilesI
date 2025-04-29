package com.example.practicaroom.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Ingrediente(
    val nombre: String
) {
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
}
