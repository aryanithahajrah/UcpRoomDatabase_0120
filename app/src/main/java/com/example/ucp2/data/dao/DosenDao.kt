package com.example.ucp2.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.ucp2.data.entity.Dosen
import kotlinx.coroutines.flow.Flow

@Dao
interface DosenDao {
    @Query("select * from dosen ORDER BY nama ASC")
    fun getAllDosen(): Flow<List<Dosen>>

    @Query("select * from dosen WHERE nidn = :nidn")
    fun getDosen(nidn: String): Flow<Dosen>

    @Insert
    suspend fun insertDosen(dosen: Dosen)
}