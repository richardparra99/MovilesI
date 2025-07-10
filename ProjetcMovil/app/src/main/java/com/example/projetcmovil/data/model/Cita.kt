package com.example.projetcmovil.data.model

data class Cita(
    val id: Int,
    val trabajador: Trabajador?,
    val categoria: Categoria?,
    val fecha: String?,
    val estado: String,
    val hora: String?,
    val confirmada: Boolean,
    val finalizada: Boolean,
    val calificada: Boolean
)
