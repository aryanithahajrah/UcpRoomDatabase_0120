package com.example.ucp2.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.ucp2.data.entity.Matakuliah
import kotlinx.coroutines.flow.Flow

@Dao
interface MatakuliahDao {
    @Query("select * from matakuliah ORDER BY nama ASC")
    fun getAllMatakuliah() : Flow<List<Matakuliah>>

    @Query("select * from matakuliah WHERE kode = :kode")
    fun getMatakuliah(kode: String) : Flow<Matakuliah>

    @Delete
    suspend fun deleteMatakuliah (matakuliah: Matakuliah)

    @Insert
    suspend fun insertMatakuliah (matakuliah: Matakuliah)

    @Update
    suspend fun updateMatakuliah (matakuliah: Matakuliah)
}