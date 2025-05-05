package com.example.practicaroom.ui.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicaroom.db.models.Receta
import com.example.practicaroom.repositories.RecetaRepository
import kotlinx.coroutines.launch

class BuscarRecetaViewModel: ViewModel() {
    private val _recetasFiltradas = MutableLiveData<List<Receta>>()
    val recetasFiltradas: LiveData<List<Receta>> = _recetasFiltradas

    fun buscarRecetas(context: Context, ingredientes: List<String>) {
        viewModelScope.launch {
            val recetas = RecetaRepository.buscarRecetasPorIngredientes(context, ingredientes)
            _recetasFiltradas.postValue(recetas)
        }
    }
    // Esto lo podés seguir usando si querés en MainActivity
    /*private var _peopleList: MutableLiveData<ArrayList<Receta>> = MutableLiveData(arrayListOf())
    val peopleList: MutableLiveData<ArrayList<Receta>> = _peopleList

    fun loadPeople(context: Context) {
        viewModelScope.launch {
            peopleList.postValue(
                RecetaRepository.obtenerListaReceta(context) as ArrayList<Receta>
            )
        }
    }*/

}