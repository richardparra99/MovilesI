package com.example.practicaroom.db.models

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class RecetaConIngrediente(
    @Embedded
    val receta: Receta,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = RecetaIngrediente::class,
            parentColumn = "recetaId",
            entityColumn = "ingredienteId"
        )
    )
    val ingrediente: List<Ingrediente>
)
