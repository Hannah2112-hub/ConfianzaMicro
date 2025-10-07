package com.example.confianzamicro.data.db

import androidx.room.*
import com.example.confianzamicro.domain.AdvisorEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AdvisorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAdvisor(advisor: AdvisorEntity)

    @Query("SELECT * FROM advisors")
    fun getAllAdvisors(): Flow<List<AdvisorEntity>>

    @Delete
    suspend fun deleteAdvisor(advisor: AdvisorEntity)

    // Login con username y password
    @Query("SELECT * FROM advisors WHERE username = :username AND password = :password LIMIT 1")
    suspend fun login(username: String, password: String): AdvisorEntity?
}
