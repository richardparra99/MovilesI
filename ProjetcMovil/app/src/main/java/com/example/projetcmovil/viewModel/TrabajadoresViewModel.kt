package com.example.projetcmovil.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.projetcmovil.data.model.Trabajador
import com.example.projetcmovil.data.repository.Repositorio
import kotlinx.coroutines.launch

class TrabajadoresViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Repositorio(application)

    private val _trabajadores = MutableLiveData<List<Trabajador>>()
    val trabajadores: LiveData<List<Trabajador>> get() = _trabajadores

    private val _mensajeError = MutableLiveData<String>()
    val mensajeError: LiveData<String> get() = _mensajeError

    fun obtenerTrabajadores(categoriaId: Int) {
        viewModelScope.launch {
            try {
                val response = repository.obtenerTrabajadoresPorCategoria(categoriaId)
                if (response.isSuccessful) {
                    _trabajadores.value = response.body()
                } else {
                    _mensajeError.value = "Error: ${response.code()}"
                }
            } catch (e: Exception) {
                _mensajeError.value = "Excepción: ${e.localizedMessage}"
            }
        }
    }
}