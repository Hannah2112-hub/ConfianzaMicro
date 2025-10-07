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

    fun loadClients() {
        viewModelScope.launch {
            repository.getClients().collect {
                _clients.value = it
            }
        }
    }

    fun addClient(client: ClientEntity) {
        viewModelScope.launch {
            repository.addClient(client)
        }
    }

    fun deleteClient(client: ClientEntity) {
        viewModelScope.launch {
            repository.deleteClient(client)
        }
    }
}
