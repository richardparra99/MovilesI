package com.example.practicaroom.ui.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.practicaroom.db.models.Receta
import com.example.practicaroom.repositories.RecetaRepository
import kotlinx.coroutines.launch

class BuscarRecetaViewModel: ViewModel() {
    private var _peopleList: MutableLiveData<ArrayList<Receta>> = MutableLiveData(arrayListOf())
    val peopleList: MutableLiveData<ArrayList<Receta>> = _peopleList

    fun buscarRecetasConIngredientes(context: Context, ingredientes: List<String>): LiveData<List<Receta>> = liveData {
        val recetas = RecetaRepository.buscarRecetasPorIngredientes(context, ingredientes)
        emit(recetas)
    }

    fun loadPeople(context: Context){
        viewModelScope.launch {
            peopleList.postValue(
                RecetaRepository.obtenerListaReceta(context) as ArrayList<Receta>
            )
        }
    }
}