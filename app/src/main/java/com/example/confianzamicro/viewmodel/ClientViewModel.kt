package com.example.confianzamicro.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.confianzamicro.domain.ClientEntity
import com.example.confianzamicro.repository.ClientRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ClientViewModel(private val repository: ClientRepository) : ViewModel() {

    private val _clients = MutableStateFlow<List<ClientEntity>>(emptyList())
    val clients: StateFlow<List<ClientEntity>> = _clients

    // 🔹 Cargar clientes desde Firebase
    fun loadClients() {
        viewModelScope.launch {
            try {
                val clientList = repository.getClients()
                _clients.value = clientList
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // 🔹 Agregar cliente a Firebase
    fun addClient(client: ClientEntity) {
        viewModelScope.launch {
            try {
                repository.addClient(client)
                loadClients() // Refrescar lista después de agregar
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // 🔹 Eliminar cliente de Firebase
    fun deleteClient(client: ClientEntity) {
        viewModelScope.launch {
            try {
                repository.deleteClient(client)
                loadClients() // Refrescar lista después de eliminar
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
