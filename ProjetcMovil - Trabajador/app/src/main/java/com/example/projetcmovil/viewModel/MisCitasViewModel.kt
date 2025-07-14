package com.example.projetcmovil.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope // ✅ IMPORTANTE
import com.example.projetcmovil.data.model.Cita
import com.example.projetcmovil.data.repository.Repositorio
import kotlinx.coroutines.launch // ✅ IMPORTANTE

class MisCitasViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Repositorio(application)

    private val _citas = MutableLiveData<List<Cita>>()
    val citas: LiveData<List<Cita>> get() = _citas

    private val _mensajeError = MutableLiveData<String>()
    val mensajeError: LiveData<String> get() = _mensajeError

    fun obtenerCitas(workerId: Int) {
        viewModelScope.launch {
            try {
                val response = repository.obtenerCitas(workerId)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _citas.postValue(it)
                    } ?: run {
                        _mensajeError.postValue("Lista vacía")
                    }
                } else {
                    _mensajeError.postValue("Error ${response.code()}")
                }
            } catch (e: Exception) {
                _mensajeError.postValue("Excepción: ${e.localizedMessage}")
            }
        }
    }
}
