package com.example.projetcmovil.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.projetcmovil.data.model.MensajeChat
import com.example.projetcmovil.data.repository.Repositorio
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatViewModel(application: Application) : AndroidViewModel(application) {

    private val _mensajes = MutableLiveData<List<MensajeChat>>()
    val mensajes: LiveData<List<MensajeChat>> get() = _mensajes

    private val repo = Repositorio(application)
    private var listaMensajes = mutableListOf<MensajeChat>()

    fun cargarMensajes(trabajadorId: Int) {
        listaMensajes = mutableListOf(
            MensajeChat(1, "Hola, ¿en qué puedo ayudarte?", false),
            MensajeChat(2, "Quiero un presupuesto", true),
            MensajeChat(3, "Claro, dime más detalles", false)
        )
        _mensajes.value = listaMensajes
    }

    fun enviarMensaje(mensaje: String) {
        listaMensajes.add(MensajeChat(listaMensajes.size + 1, mensaje, true))
        _mensajes.value = listaMensajes
    }

    fun reservarCita(trabajadorId: Int, callback: (Boolean) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val respuesta = repo.crearCita(trabajadorId)
                CoroutineScope(Dispatchers.Main).launch {
                    callback(respuesta.isSuccessful)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                CoroutineScope(Dispatchers.Main).launch {
                    callback(false)
                }
            }
        }
    }

}