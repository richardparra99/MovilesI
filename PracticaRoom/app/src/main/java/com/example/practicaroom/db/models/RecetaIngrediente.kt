package com.example.practicaroom.db.models

import androidx.room.Entity

@Entity(primaryKeys = ["recetaID", "ingredienteId"])
data class RecetaIngrediente(
    val recetaId: Int,
    val ingredienteId: Int
)
