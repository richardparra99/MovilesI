package com.example.practicaroom.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.practicaroom.db.models.Receta

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

    @Query("""
    SELECT * FROM Receta
    WHERE ingredientes LIKE '%' || :ingrediente1 || '%'
       OR ingredientes LIKE '%' || :ingrediente2 || '%'
       OR ingredientes LIKE '%' || :ingrediente3 || '%'
       OR ingredientes LIKE '%' || :ingrediente4 || '%'
       OR ingredientes LIKE '%' || :ingrediente5 || '%'
       OR ingredientes LIKE '%' || :ingrediente6 || '%'
       OR ingredientes LIKE '%' || :ingrediente7 || '%'
       OR ingredientes LIKE '%' || :ingrediente8 || '%'
""")
    suspend fun buscarRecetasPorIngredientes(
        ingrediente1: String = "",
        ingrediente2: String = "",
        ingrediente3: String = "",
        ingrediente4: String = "",
        ingrediente5: String = "",
        ingrediente6: String = "",
        ingrediente7: String = "",
        ingrediente8: String = ""
    ): List<Receta>

}