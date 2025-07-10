package com.example.projetcmovil.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.projetcmovil.data.model.Trabajador
import com.example.projetcmovil.data.repository.Repositorio
import kotlinx.coroutines.launch

class DetalleTrabajadorViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Repositorio(application)
    private val _trabajador = MutableLiveData<Trabajador>()
    val trabajador: LiveData<Trabajador> get() = _trabajador
    private val _mensajeError = MutableLiveData<String>()
    val mensajeError: LiveData<String> get() = _mensajeError

    fun obtenerDetalleTrabajador(id: Int) {
        viewModelScope.launch {
            try {
                val response = repository.obtenerDetalleTrabajador(id)
                if (response.isSuccessful) {
                    _trabajador.value = response.body()
                } else {
                    _mensajeError.value = "Error: ${response.code()}"
                }
            } catch (e: Exception) {
                _mensajeError.value = "Excepción: ${e.localizedMessage}"
            }
        }
    }
}