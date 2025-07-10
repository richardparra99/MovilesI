package com.example.projetcmovil.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.projetcmovil.data.model.Categoria
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
                    _categorias.value = response.body()
                } else {
                    _mensajeError.value = "Error: ${response.code()}"
                }
            } catch (e: Exception) {
                _mensajeError.value = "Excepción: ${e.localizedMessage}"
            }
        }
    }
}