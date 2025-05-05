package com.example.practicaroom.ui.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicaroom.db.AppDataBase
import com.example.practicaroom.db.models.RecetaConIngrediente
import kotlinx.coroutines.launch

class DetalleListaRecetaViewModel: ViewModel() {
    private val _recetaConIngredientes = MutableLiveData<RecetaConIngrediente?>()
    val recetaConIngredientes: LiveData<RecetaConIngrediente?> = _recetaConIngredientes

    fun cargarReceta(context: Context, recetaId: Int) {
        viewModelScope.launch {
            val db = AppDataBase.getInstance(context)
            val receta = db.recetaDao().obtenerRecetaConIngredientesPorId(recetaId)
            _recetaConIngredientes.postValue(receta)
        }
    }
}