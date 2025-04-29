package com.example.practicaroom.repositories

import android.content.Context
import com.example.practicaroom.db.AppDataBase
import com.example.practicaroom.db.dao.RecetaDao
import com.example.practicaroom.db.models.Receta
import com.example.practicaroom.db.models.RecetaIngrediente

object RecetaRepository {

    suspend fun insertarReceta(context: Context, receta: Receta) {
        val db = AppDataBase.getInstance(context)
        db.recetaDao().insertarReceta(receta)
    }

    suspend fun guardarReceta(context: Context, receta: Receta){
        if (receta.id == 0){
            insertarReceta(context, receta)
        } else {
            actualizarReceta(context, receta)
        }
    }

    suspend fun getById(context: Context, id: Int): Receta {
        val db = AppDataBase.getInstance(context)
        return db.recetaDao().obtenerRecetasPorId(id)
    }

    suspend fun obtenerListaReceta(context: Context): List<Receta>{
        return AppDataBase
            .getInstance(context)
            .recetaDao()
            .obtenerTodaLaReceta()
    }

    suspend fun actualizarReceta(context: Context, receta: Receta){
        AppDataBase
            .getInstance(context)
            .recetaDao()
            .actualizarReceta(receta)
    }

    suspend fun EliminarReceta(context: Context, receta: Receta){
        AppDataBase
            .getInstance(context)
            .recetaDao()
            .eliminarReceta(receta)
    }

    suspend fun buscarRecetasPorIngredientes(context: Context, ingredientes: List<String>): List<Receta> {
        val db = AppDataBase.getInstance(context)

        // Preparamos hasta 8 ingredientes, si hay menos rellenamos con ""
        val listaIngredientes = ingredientes.toMutableList()
        while (listaIngredientes.size < 8) {
            listaIngredientes.add("")
        }

        return db.recetaDao().buscarRecetasPorIngredientes(
            listaIngredientes[0],
            listaIngredientes[1],
            listaIngredientes[2],
            listaIngredientes[3],
            listaIngredientes[4],
            listaIngredientes[5],
            listaIngredientes[6],
            listaIngredientes[7]
        )
    }
}