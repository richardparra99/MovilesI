package com.example.practicaroom.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RecetaDetalleViewModel: ViewModel() {
    private val _ingredientesSeleccionados = MutableLiveData<MutableSet<String>>(mutableSetOf())
    val ingredientesSeleccionados: LiveData<MutableSet<String>> = _ingredientesSeleccionados

    fun toggleIngrediente(nombre: String) {
        val set = _ingredientesSeleccionados.value ?: mutableSetOf()
        if (set.contains(nombre)) {
            set.remove(nombre)
        } else {
            set.add(nombre)
        }
        _ingredientesSeleccionados.value = set
    }

    fun setIngredientesIniciales(ingredientes: List<String>) {
        _ingredientesSeleccionados.value = ingredientes.toMutableSet()
    }
}