package com.example.projetcmovil.data.repository

import android.content.Context
import com.example.projetcmovil.data.model.CitaRequest
import com.example.projetcmovil.data.network.InstanciaRetrofit
import com.example.projetcmovil.util.GestorToken

class Repositorio(private val context: Context) {
    private val api = InstanciaRetrofit.obtenerInstancia(context)
    private val gestorToken = GestorToken(context)

    suspend fun login(email: String, password: String) =
        api.login(mapOf("email" to email, "password" to password))

    suspend fun registro(nombre: String, apellido: String, email: String, password: String) =
        api.registro(mapOf("name" to nombre, "lastName" to apellido, "email" to email, "password" to password))

    suspend fun obtenerCategorias() = api.obtenerCategorias()

    suspend fun obtenerTrabajadoresPorCategoria(categoriaId: Int) =
        api.obtenerTrabajadoresPorCategoria(categoriaId.toString())


    suspend fun obtenerDetalleTrabajador(idTrabajador: Int) =
        api.obtenerDetalleTrabajador(idTrabajador)

    suspend fun crearCita(idTrabajador: Int) =
        api.crearCita(CitaRequest(idTrabajador))

//
//    suspend fun obtenerCitas() = api.obtenerCitas()
//
//    suspend fun obtenerDetalleCita(idCita: Int) = api.obtenerDetalleCita(idCita)
//
//    suspend fun concretarCita(idCita: Int, fecha: String, hora: String, ubicacion: String) =
//        api.concretarCita(idCita, mapOf("date" to fecha, "time" to hora, "location" to ubicacion))
//
//    suspend fun obtenerMensajes(idCita: Int) = api.obtenerMensajes(idCita)
//
//    suspend fun enviarMensaje(idCita: Int, mensaje: String) =
//        api.enviarMensaje(idCita, mapOf("message" to mensaje))
//
//    suspend fun calificarCita(idCita: Int, puntuacion: Int, comentario: String) =
//        api.calificarCita(idCita, mapOf("rating" to puntuacion, "comment" to comentario))

    fun guardarToken(token: String) = gestorToken.guardarToken(token)
    fun obtenerToken() = gestorToken.obtenerToken()
    fun borrarToken() = gestorToken.borrarToken()
}