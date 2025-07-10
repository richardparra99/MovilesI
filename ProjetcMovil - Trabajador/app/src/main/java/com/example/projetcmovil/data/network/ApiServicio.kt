package com.example.projetcmovil.data.network

import com.example.projetcmovil.data.model.Categoria
import com.example.projetcmovil.data.model.Trabajador
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiServicio {
    @POST("workers/register")
    suspend fun registrarTrabajador(
        @Body datos: Map<String, String>
    ): Response<Trabajador>

    @GET("categories")
    suspend fun obtenerCategorias(): Response<List<Categoria>>
}