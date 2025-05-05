package com.example.practicaroom.ui.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicaroom.db.models.Receta
import com.example.practicaroom.repositories.RecetaRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel(){
    private var _peopleList: MutableLiveData<ArrayList<Receta>> = MutableLiveData(arrayListOf())
    val peopleList: MutableLiveData<ArrayList<Receta>> = _peopleList

    fun loadPeople(context: Context) {
        viewModelScope.launch {
            val lista = RecetaRepository.obtenerListaReceta(context)
            _peopleList.postValue(ArrayList(lista))
        }
    }
}