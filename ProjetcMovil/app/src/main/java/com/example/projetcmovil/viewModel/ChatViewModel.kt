package com.example.projetcmovil.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.projetcmovil.data.model.MensajeChat

class ChatViewModel(application: Application) : AndroidViewModel(application) {

    private val _mensajes = MutableLiveData<List<MensajeChat>>()
    val mensajes: LiveData<List<MensajeChat>> get() = _mensajes

    private var listaMensajes = mutableListOf<MensajeChat>()

    fun cargarMensajes(trabajadorId: Int) {
        // Aquí puedes hacer un API para traer mensajes por trabajador
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
}