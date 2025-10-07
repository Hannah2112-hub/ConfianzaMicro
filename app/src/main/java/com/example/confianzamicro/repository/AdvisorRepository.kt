package com.example.confianzamicro.repository

import com.example.confianzamicro.data.db.AdvisorDao
import com.example.confianzamicro.domain.AdvisorEntity
import kotlinx.coroutines.flow.Flow

class AdvisorRepository(private val dao: AdvisorDao) {

    suspend fun insertAdvisor(advisor: AdvisorEntity) {
        dao.insertAdvisor(advisor)
    }

    fun getAllAdvisors(): Flow<List<AdvisorEntity>> = dao.getAllAdvisors()

    suspend fun deleteAdvisor(advisor: AdvisorEntity) {
        dao.deleteAdvisor(advisor)
    }

    suspend fun login(username: String, password: String): AdvisorEntity? {
        return dao.login(username, password)
    }
}
