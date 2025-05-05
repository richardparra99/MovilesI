package com.example.practicaroom.ui.viewmodels

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicaroom.db.models.Receta
import kotlinx.coroutines.launch

class ListaRecetaFiltradasViewModel: ViewModel() {
    private val _recetasFiltradas = MutableLiveData<List<Receta>>()
    val recetasFiltradas: LiveData<List<Receta>> = _recetasFiltradas

    fun cargarRecetasDesdeBundle(bundle: Bundle?) {
        viewModelScope.launch {
            val extra = bundle?.getSerializable("recetas_encontradas")
            val listaFiltrada = (extra as? ArrayList<*>)?.filterIsInstance<Receta>() ?: emptyList()
            _recetasFiltradas.postValue(listaFiltrada)
        }
    }
}