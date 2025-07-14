package com.example.projetcmovil.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetcmovil.data.repository.Repositorio
import kotlinx.coroutines.launch

class LoginTrabajoViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Repositorio(application)

    fun login(
        correo: String,
        contrasena: String,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = repository.login(correo, contrasena)
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        repository.guardarToken(body.access_token)
                        onSuccess(body.access_token)
                    } else {
                        onError("No se recibió respuesta del servidor")
                    }
                } else {
                    onError("Error ${response.code()}: ${response.message()}")
                }
            } catch (e: Exception) {
                onError("Error: ${e.localizedMessage}")
            }
        }
    }
}