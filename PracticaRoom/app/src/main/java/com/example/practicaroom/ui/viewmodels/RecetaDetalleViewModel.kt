package com.example.practicaroom.ui.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicaroom.db.AppDataBase
import com.example.practicaroom.db.models.Receta
import kotlinx.coroutines.launch

class RecetaDetalleViewModel: ViewModel() {
    private val _ingredientesSeleccionados = MutableLiveData<MutableList<String>>(mutableListOf())
    val ingredientesSeleccionados: LiveData<MutableList<String>> = _ingredientesSeleccionados

    fun setIngredientesIniciales(lista: List<String>) {
        _ingredientesSeleccionados.value = lista.toMutableList()
    }

    fun toggleIngrediente(nombre: String) {
        val lista = _ingredientesSeleccionados.value ?: mutableListOf()
        if (lista.contains(nombre)) {
            lista.remove(nombre)
        } else {
            lista.add(nombre)
        }
        _ingredientesSeleccionados.value = lista
    }

    /*fun guardarReceta(context: Context, receta: Receta, onFinish: () -> Unit) {
        val ingredientes = _ingredientesSeleccionados.value?.toList() ?: emptyList()
        viewModelScope.launch {
            RecetaRepository.guardarRecetaConIngredientes(context, receta, ingredientes)
            onFinish()
        }
    }

    fun eliminarReceta(context: Context, receta: Receta, onFinish: () -> Unit) {
        viewModelScope.launch {
            RecetaRepository.EliminarReceta(context, receta)
            onFinish()
        }
    }*/

    fun cargarIngredientes(context: Context, receta: Receta?, preseleccionados: List<String>?) {
        viewModelScope.launch {
            if (receta != null && receta.id != 0) {
                val recetaConIngredientes = AppDataBase.getInstance(context)
                    .recetaDao()
                    .obtenerRecetaConIngredientesPorId(receta.id)

                recetaConIngredientes?.let {
                    val nombres = it.ingrediente.map { i -> i.nombre }
                    _ingredientesSeleccionados.postValue(nombres.toMutableList())
                }
            } else {
                preseleccionados?.let {
                    _ingredientesSeleccionados.postValue(it.toMutableList())
                }
            }
        }
    }
}