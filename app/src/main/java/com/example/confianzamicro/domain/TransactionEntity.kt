package com.example.confianzamicro.domain

data class TransactionEntity(
    val id: String = "", // id del documento en Firebase
    val idClient: String = "",
    val amount: Double = 0.0,
    val date: String = "",
    val type: String = "",
    val description: String = ""
)