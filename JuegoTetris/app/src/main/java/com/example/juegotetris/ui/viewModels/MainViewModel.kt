package com.example.juegotetris.ui.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.juegotetris.db.Score
import com.example.juegotetris.db.appDataBase.TetrisDataBase
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    fun guardarPuntaje(context: Context, nombre: String, puntaje: Int) {
        viewModelScope.launch {
            val db = TetrisDataBase.getInstance(context)
            db.scoreDao().insertScore(Score(playerName = nombre, score = puntaje))
        }
    }
}