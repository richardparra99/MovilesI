package com.example.projetcmovil.data.repository

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import com.example.projetcmovil.data.model.CategoriaRequest
import com.example.projetcmovil.data.model.OnlyId
import com.example.projetcmovil.data.model.TokenResponse
import com.example.projetcmovil.data.model.Trabajador
import com.example.projetcmovil.data.network.InstanciaRetrofit
import com.example.projetcmovil.util.GestorToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

class Repositorio(private val context: Context) {
    private val api = InstanciaRetrofit.obtenerInstancia(context)
    private val gestorToken = GestorToken(context)

    suspend fun login(correo: String, contrasena: String) =
        api.login(mapOf(
            "email" to correo,
            "password" to contrasena
        ))

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

    suspend fun agregarOcupaciones(usuarioId: Int, categorias: List<OnlyId>) =
        api.agregarOcupaciones(usuarioId, CategoriaRequest(categorias))


    suspend fun subirFotoPerfil( uri: Uri) = withContext(Dispatchers.IO) {
        val contentResolver = context.contentResolver
        val inputStream = contentResolver.openInputStream(uri)
        val fileName = obtenerNombreArchivo(uri)

        val bytes = inputStream?.readBytes()
        val requestFile = bytes?.toRequestBody("image/*".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("picture", fileName, requestFile!!)

        api.subirFotoPerfil( body)
    }

    private fun obtenerNombreArchivo(uri: Uri): String {
        var nombre = "imagen.jpg"
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val index = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (index >= 0) nombre = it.getString(index)
            }
        }
        return nombre
    }

    suspend fun obtenerCitas(workerId: Int) =
        api.obtenerCitas(workerId)


//
//    suspend fun obtenerCitas() = api.obtenerCitas()



    fun guardarToken(token: String) = gestorToken.guardarToken(token)
    fun obtenerToken() = gestorToken.obtenerToken()
    fun borrarToken() = gestorToken.borrarToken()


    fun guardarWorkerId(workerId: Int) {
        context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
            .edit()
            .putInt("WORKER_ID", workerId)
            .apply()
    }

    fun obtenerWorkerId(): Int {
        return context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
            .getInt("WORKER_ID", 0)
    }


}