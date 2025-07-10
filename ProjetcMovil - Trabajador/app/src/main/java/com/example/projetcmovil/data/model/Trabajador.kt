package com.example.projetcmovil.data.model

data class Trabajador(
    val id: Int,
    val name: String,
    val email: String,
    val profile: Perfil,
    val worker: Obrero
)
