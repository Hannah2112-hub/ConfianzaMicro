package com.example.confianzamicro.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.confianzamicro.domain.AdvisorEntity
import com.example.confianzamicro.repository.AdvisorRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AdvisorViewModel(private val repository: AdvisorRepository) : ViewModel() {

    private val _advisors = MutableStateFlow<List<AdvisorEntity>>(emptyList())
    val advisors: StateFlow<List<AdvisorEntity>> = _advisors

    fun loadAdvisors() {
        viewModelScope.launch {
            repository.getAllAdvisors().collect { list ->
                _advisors.value = list
            }
        }
    }

    fun addAdvisor(advisor: AdvisorEntity) {
        viewModelScope.launch {
            repository.insertAdvisor(advisor)
        }
    }

    fun deleteAdvisor(advisor: AdvisorEntity) {
        viewModelScope.launch {
            repository.deleteAdvisor(advisor)
        }
    }

    suspend fun login(username: String, password: String): AdvisorEntity? {
        return repository.login(username, password)
    }
}
