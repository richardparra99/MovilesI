package com.example.projetcmovil.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetcmovil.data.repository.Repositorio
import kotlinx.coroutines.launch

class LoginViewModel (application: Application) : AndroidViewModel(application) {
    private val repository = Repositorio(application)

    fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = repository.login(email, password)
                if (response.isSuccessful) {
                    val token = response.body()?.get("access_token")
                    if (!token.isNullOrEmpty()) {
                        repository.guardarToken(token)
                        onSuccess()
                    } else {
                        onError("Token vacío")
                    }
                } else {
                    onError("Error: ${response.code()}")
                }
            } catch (e: Exception) {
                onError("Excepción: ${e.localizedMessage}")
            }
        }
    }
}