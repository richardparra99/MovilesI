package com.example.projetcmovil.data.model

data class Obrero(
    val id: Int,
    val user_id: Int,
    val picture_url: String?,
    val average_rating: Float,
    val reviews_count: Int,
    val user: Cliente,
    val categories: List<Categoria>
)
