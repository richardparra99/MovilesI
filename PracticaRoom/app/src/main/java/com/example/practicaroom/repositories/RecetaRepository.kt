package com.example.practicaroom.repositories

import android.content.Context
import com.example.practicaroom.db.AppDataBase
import com.example.practicaroom.db.models.Ingrediente
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

    suspend fun buscarRecetasPorIngredientes(context: Context, ingredientesSeleccionados: List<String>): List<Receta> {
        val db = AppDataBase.getInstance(context)

        val recetasRelacionadas = db.recetaDao().obtenerRecetasConIngredientes()

        return recetasRelacionadas.filter { recetaConIngrediente ->
            // Verifica si al menos un ingrediente de la receta coincide con los seleccionados
            ingredientesSeleccionados.any { seleccionado ->
                recetaConIngrediente.ingrediente.any { it.nombre.equals(seleccionado, ignoreCase = true) }
            }
        }.map { it.receta }
    }

    suspend fun guardarRecetaConIngredientes(context: Context, receta: Receta, nombresIngredientes: List<String>): Receta {
        val db = AppDataBase.getInstance(context)

        val recetaId = db.recetaDao().insertarReceta(receta).toInt()
        receta.id = recetaId // MUY IMPORTANTE

        for (nombreOriginal in nombresIngredientes) {
            val nombre = nombreOriginal.trim().lowercase()

            var ingrediente = db.recetaDao().obtenerIngredientePorNombre(nombre)

            if (ingrediente == null) {
                val nuevoIngredienteId = db.recetaDao().insertarIngrediente(Ingrediente(nombre)).toInt()
                ingrediente = Ingrediente(nombre).apply { id = nuevoIngredienteId }
            }

            db.recetaDao().insertarRecetaIngrediente(
                RecetaIngrediente(recetaId, ingrediente.id)
            )
        }

        return receta // ✅ retorna la receta con ID actualizado
    }


}