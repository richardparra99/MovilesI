package com.example.projetcmovil.data.repository

import android.content.Context
import com.example.projetcmovil.data.network.InstanciaRetrofit
import com.example.projetcmovil.util.GestorToken

class Repositorio(private val context: Context) {
    private val api = InstanciaRetrofit.obtenerInstancia(context)
    private val gestorToken = GestorToken(context)

    suspend fun registrarTrabajador(
        nombre: String,
        apellido: String,
        correo: String,
        contrasena: String
    ) = api.registrarTrabajador(
        mapOf(
            "name" to nombre,
            "lastName" to apellido,
            "email" to correo,
            "password" to contrasena
        )
    )

    suspend fun obtenerCategorias() = api.obtenerCategorias()

    fun guardarToken(token: String) = gestorToken.guardarToken(token)
    fun obtenerToken() = gestorToken.obtenerToken()
    fun borrarToken() = gestorToken.borrarToken()
}