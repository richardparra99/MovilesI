package com.example.projetcmovil.data.model

data class Trabajador(
    val id: Int,
    val user_id: Int,
    val picture_url: String?,
    val average_rating: Double,
    val reviews_count: Int,
    val user: Usuario
)
