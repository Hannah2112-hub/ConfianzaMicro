package com.example.confianzamicro.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.confianzamicro.domain.AdvisorEntity
import com.example.confianzamicro.repository.AdvisorRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AdvisorViewModel(
    private val repository: AdvisorRepository = AdvisorRepository() // ahora no necesita DAO
) : ViewModel() {

    private val _advisors = MutableStateFlow<List<AdvisorEntity>>(emptyList())
    val advisors: StateFlow<List<AdvisorEntity>> = _advisors

    // 🔹 Carga todos los asesores desde Firestore
    fun loadAdvisors() {
        viewModelScope.launch {
            val list = repository.getAllAdvisors()
            _advisors.value = list
        }
    }

    // 🔹 Agrega un nuevo asesor
    fun addAdvisor(advisor: AdvisorEntity) {
        viewModelScope.launch {
            repository.insertAdvisor(advisor)
            loadAdvisors() // recargar lista después de insertar
        }
    }

    // 🔹 Elimina un asesor
    fun deleteAdvisor(advisor: AdvisorEntity) {
        viewModelScope.launch {
            repository.deleteAdvisor(advisor)
            loadAdvisors() // recargar lista después de eliminar
        }
    }

    // 🔹 Login
    suspend fun login(username: String, password: String): AdvisorEntity? {
        return repository.login(username, password)
    }
}
