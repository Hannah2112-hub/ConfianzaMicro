package com.example.confianzamicro.repository

import com.example.confianzamicro.data.db.ClientDao
import com.example.confianzamicro.domain.ClientEntity
import kotlinx.coroutines.flow.Flow

class ClientRepository(private val clientDao: ClientDao) {

    fun getClients(): Flow<List<ClientEntity>> = clientDao.getAll()

    suspend fun addClient(client: ClientEntity) {
        clientDao.insert(client)
    }

    suspend fun deleteClient(client: ClientEntity) {
        clientDao.delete(client)
    }
}
