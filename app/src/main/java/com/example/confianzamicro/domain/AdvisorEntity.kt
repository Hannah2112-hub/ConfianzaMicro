package com.example.confianzamicro.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "advisors")
data class AdvisorEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val username: String,
    val password: String
)
