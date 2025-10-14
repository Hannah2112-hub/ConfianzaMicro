package com.example.confianzamicro.domain

data class ClientEntity(
    val id: String = "",      // ID del documento en Firebase
    val name: String = "",
    val dni: String = "",
    val phone: String = "",
    val address: String = ""
)
