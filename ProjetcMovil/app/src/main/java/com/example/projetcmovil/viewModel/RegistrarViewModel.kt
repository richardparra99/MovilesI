package com.example.projetcmovil.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetcmovil.data.repository.Repositorio
import kotlinx.coroutines.launch

class RegistrarViewModel (application: Application) : AndroidViewModel(application) {
    private val repository = Repositorio(application)

    fun registrarCliente(
        nombre: String,
        apellido: String,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = repository.registro(nombre, apellido, email, password)
                if (response.isSuccessful) {
                    println(">>> REGISTRO EXITOSO")
                    onSuccess()
                } else {
                    val errorBody = response.errorBody()?.string()
                    println(">>> ERROR REGISTRO: ${response.code()} -> $errorBody")
                    onError("Error: ${response.code()}")
                }
            } catch (e: Exception) {
                println(">>> EXCEPCIÓN REGISTRO: ${e.localizedMessage}")
                onError("Excepción: ${e.localizedMessage}")
            }
        }
    }
}