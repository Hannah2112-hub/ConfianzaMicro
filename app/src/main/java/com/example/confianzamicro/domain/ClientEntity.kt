package com.example.confianzamicro.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clients")
data class ClientEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val dni: String,
    val phone: String,
    val address: String
)
