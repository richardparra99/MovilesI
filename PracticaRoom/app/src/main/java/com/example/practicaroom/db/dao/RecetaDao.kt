package com.example.practicaroom.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.practicaroom.db.models.Ingrediente
import com.example.practicaroom.db.models.Receta
import com.example.practicaroom.db.models.RecetaConIngrediente
import com.example.practicaroom.db.models.RecetaIngrediente

@Dao
interface RecetaDao {
    @Query("Select * from Receta")
    suspend fun obtenerTodaLaReceta(): List<Receta>

    @Query("Select * from Receta where id = :id")
    suspend fun obtenerRecetasPorId(id: Int): Receta

    @Insert
    suspend fun insertarReceta(receta: Receta): Long

    @Update
    suspend fun actualizarReceta(receta: Receta)

    @Delete
    suspend fun eliminarReceta(receta: Receta)

    @Transaction
    @Query("""
        SELECT * FROM Receta
        INNER JOIN RecetaIngrediente ON Receta.id = RecetaIngrediente.recetaId
        INNER JOIN Ingrediente ON Ingrediente.id = RecetaIngrediente.ingredienteId
        WHERE Ingrediente.nombre IN (:nombres)
        GROUP BY Receta.id
    """)
    suspend fun buscarRecetasPorNombresDeIngredientes(nombres: List<String>): List<RecetaConIngrediente>


    @Insert
    suspend fun insertarIngrediente(ingrediente: Ingrediente): Long

    @Query("SELECT * FROM Ingrediente WHERE nombre = :nombre")
    suspend fun obtenerIngredientePorNombre(nombre: String): Ingrediente?

    @Insert
    suspend fun insertarRecetaIngrediente(crossRef: RecetaIngrediente)

    @Transaction
    @Query("SELECT * FROM Receta")
    suspend fun obtenerRecetasConIngredientes(): List<RecetaConIngrediente>


    @Transaction
    @Query("SELECT * FROM Receta WHERE id = :id")
    suspend fun obtenerRecetaConIngredientesPorId(id: Int): RecetaConIngrediente?

    @Query("DELETE FROM RecetaIngrediente WHERE recetaId = :recetaId")
    suspend fun eliminarIngredientesDeReceta(recetaId: Int)


}