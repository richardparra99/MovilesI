package com.example.projetcmovil.data.network

import com.example.projetcmovil.data.model.Categoria
import com.example.projetcmovil.data.model.CategoriaRequest
import com.example.projetcmovil.data.model.Cita
import com.example.projetcmovil.data.model.TokenResponse
import com.example.projetcmovil.data.model.Trabajador
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiServicio {

    @POST("worker/login")
    suspend fun login(
        @Body datos: Map<String, String>
    ): Response<TokenResponse>

    @POST("worker/register")
    suspend fun registrarTrabajador(
        @Body datos: Map<String, String>
    ): Response<Trabajador>

    @GET("categories")
    suspend fun obtenerCategorias(): Response<List<Categoria>>

    @POST("workers/{id}/categories")
    suspend fun agregarOcupaciones(
        @Path("id") id: Int,
        @Body categorias: CategoriaRequest
    ): Response<Unit>


    @Multipart
    @POST("workers/profile-picture")
    suspend fun subirFotoPerfil(
        @Part imagen: MultipartBody.Part
    ): Response<Unit>

    @GET("worker/{id}/appointments")
    suspend fun obtenerCitas(@Path("id") id: Int): Response<List<Cita>>


//    @GET("appointments")
//    suspend fun obtenerCitas(): Response<List<Cita>>

}