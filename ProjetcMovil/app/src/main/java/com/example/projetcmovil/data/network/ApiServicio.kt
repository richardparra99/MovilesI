package com.example.projetcmovil.data.network

import com.example.projetcmovil.data.model.Categoria
import com.example.projetcmovil.data.model.Cita
import com.example.projetcmovil.data.model.MensajeChat
import com.example.projetcmovil.data.model.Trabajador
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiServicio {

    @POST("client/login")
    suspend fun login(@Body usuario: Map<String, String>): Response<Map<String, String>> // Aq


    @POST("client/register")
    suspend fun registro(@Body usuario: Map<String, String>): Response<Unit>

    // Categorías
    @GET("categories")
    suspend fun obtenerCategorias(): Response<List<Categoria>>

    @GET("categories/{id}/workers")
    suspend fun obtenerTrabajadoresPorCategoria(@Path("id") categoria: String): Response<List<Trabajador>>

    // Trabajadores
    @GET("workers/{id}")
    suspend fun obtenerDetalleTrabajador(@Path("id") id: Int): Response<Trabajador>

//    // Citas
//    @POST("/appointments")
//    suspend fun crearCita(@Body datos: Map<String, Any>): Response<Cita>
//
//    @GET("/appointments")
//    suspend fun obtenerCitas(): Response<List<Cita>>
//
//    @GET("/appointments/{id}")
//    suspend fun obtenerDetalleCita(@Path("id") idCita: Int): Response<Cita>
//
//    @POST("/appointments/{id}/make")
//    suspend fun concretarCita(@Path("id") idCita: Int, @Body datos: Map<String, Any>): Response<Unit>
//
//    // Chats
//    @POST("/appointments/{id}/chats")
//    suspend fun enviarMensaje(@Path("id") idCita: Int, @Body datos: Map<String, String>): Response<Unit>
//
//    @GET("/appointments/{id}/chats")
//    suspend fun obtenerMensajes(@Path("id") idCita: Int): Response<List<MensajeChat>>
//
//    // Reseñas
//    @POST("/appointments/{id}/review")
//    suspend fun calificarCita(@Path("id") idCita: Int, @Body datos: Map<String, Any>): Response<Unit>
}