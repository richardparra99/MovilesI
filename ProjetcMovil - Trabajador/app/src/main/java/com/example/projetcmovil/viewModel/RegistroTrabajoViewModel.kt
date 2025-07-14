package com.example.projetcmovil.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetcmovil.data.model.Trabajador
import com.example.projetcmovil.data.repository.Repositorio
import kotlinx.coroutines.launch

class RegistroTrabajoViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Repositorio(application)

    fun registrarTrabajador(
        nombre: String,
        apellido: String,
        correo: String,
        contrasena: String,
        onSuccess: (Int) -> Unit,   // ahora devolverá el workerId
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = repository.registrarTrabajador(nombre, apellido, correo, contrasena)
                if (response.isSuccessful) {
                    val token = repository.login(correo, contrasena)
                    if (token.isSuccessful) {
                        val body = token.body()
                        body?.let {
                            repository.guardarToken(it.access_token)
                            onSuccess(response.body()!!.id)
                        } ?: onError("No se recibió token")
                    } else {
                        onError("Error ${response.code()}: ${response.message()}")
                    }
                } else {
                    onError("Error ${response.code()}: ${response.message()}")
                }
            } catch (e: Exception) {
                onError("Excepción: ${e.localizedMessage}")
            }
        }
    }
}