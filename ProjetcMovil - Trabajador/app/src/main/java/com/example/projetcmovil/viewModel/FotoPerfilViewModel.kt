package com.example.projetcmovil.viewModel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.projetcmovil.data.repository.Repositorio
import kotlinx.coroutines.launch

class FotoPerfilViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Repositorio(application)

    private val _mensaje = MutableLiveData<String>()
    val mensaje: LiveData<String> get() = _mensaje

    private val _exito = MutableLiveData<Boolean>()
    val exito: LiveData<Boolean> get() = _exito

    fun subirFotoPerfil( imagenUri: Uri) {
        viewModelScope.launch {
            try {
                val response = repository.subirFotoPerfil(imagenUri)
                if (response.isSuccessful) {
                    _mensaje.postValue("Foto subida correctamente")
                    _exito.postValue(true)
                } else {
                    _mensaje.postValue("Error ${response.code()}: ${response.message()}")
                }
            } catch (e: Exception) {
                _mensaje.postValue("Excepción: ${e.localizedMessage}")
            }
        }
    }
}