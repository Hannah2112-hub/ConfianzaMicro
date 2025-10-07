package com.example.confianzamicro.data.db

import androidx.room.*
import com.example.confianzamicro.domain.ClientEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ClientDao {

    @Query("SELECT * FROM clients ORDER BY id DESC")
    fun getAll(): Flow<List<ClientEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(client: ClientEntity)

    @Delete
    suspend fun delete(client: ClientEntity)
}
