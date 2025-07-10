package com.example.projetcmovil.data.model

import com.google.gson.annotations.SerializedName

data class Categoria(
    val id: Int,
    @SerializedName("name") val nombre: String
)
