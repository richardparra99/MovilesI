package com.example.practicaroom.repositories

import android.content.Context
import com.example.practicaroom.db.AppDataBase
import com.example.practicaroom.db.models.Ingrediente
import com.example.practicaroom.db.models.Receta
import com.example.practicaroom.db.models.RecetaConIngrediente
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

    suspend fun guardarRecetaConIngredientes(
        context: Context,
        receta: Receta,
        nombresIngredientes: List<String>
    ): Receta {
        val db = AppDataBase.getInstance(context)
        val dao = db.recetaDao()

        if (receta.id != 0) {
            dao.actualizarReceta(receta)
            dao.eliminarIngredientesDeReceta(receta.id)
        } else {
            val nuevoId = dao.insertarReceta(receta).toInt()
            receta.id = nuevoId
        }

        for (nombre in nombresIngredientes) {
            val nombreLimpio = nombre.trim().lowercase()
            var ingrediente = dao.obtenerIngredientePorNombre(nombreLimpio)

            if (ingrediente == null) {
                val idNuevo = dao.insertarIngrediente(Ingrediente(nombreLimpio)).toInt()
                ingrediente = Ingrediente(nombreLimpio).apply { id = idNuevo }
            }

            dao.insertarRecetaIngrediente(RecetaIngrediente(receta.id, ingrediente.id))
        }

        return receta
    }

    suspend fun getRecetaConIngredientes(context: Context, recetaId: Int): RecetaConIngrediente? {
        return AppDataBase.getInstance(context)
            .recetaDao()
            .obtenerRecetaConIngredientesPorId(recetaId)
    }

}