package com.example.projetcmovil.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.projetcmovil.data.model.Categoria
import com.example.projetcmovil.data.model.OnlyId
import com.example.projetcmovil.data.repository.Repositorio
import kotlinx.coroutines.launch

class OcupacionViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Repositorio(application)

    private val _categorias = MutableLiveData<List<Categoria>>()
    val categorias: LiveData<List<Categoria>> get() = _categorias

    private val _mensajeError = MutableLiveData<String>()
    val mensajeError: LiveData<String> get() = _mensajeError

    fun obtenerCategorias() {
        viewModelScope.launch {
            try {
                val response = repository.obtenerCategorias()
                if (response.isSuccessful) {
                    response.body()?.let {
                        _categorias.postValue(it)
                    } ?: run {
                        _mensajeError.postValue("Lista vacía desde servidor")
                    }
                } else {
                    _mensajeError.postValue("Error: ${response.code()}")
                }
            } catch (e: Exception) {
                _mensajeError.postValue("Excepción: ${e.localizedMessage}")
            }
        }
    }

    fun agregarOcupaciones(
        userId: Int,
        categorias: List<OnlyId>,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = repository.agregarOcupaciones(userId, categorias)
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