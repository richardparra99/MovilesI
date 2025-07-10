package com.example.projetcmovil.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetcmovil.data.repository.Repositorio
import kotlinx.coroutines.launch

class RegistroTrabajoViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Repositorio(application)

    fun registrarTrabajador(
        nombre: String,
        apellido: String,
        correo: String,
        contrasena: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = repository.registro(
                    nombre,
                    apellido,
                    correo,
                    contrasena
                )
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    onError("Error ${response.code()}: ${response.message()}")
                }
            } catch (e: Exception) {
                onError("Excepción: ${e.localizedMessage}")
            }
        }
    }
}