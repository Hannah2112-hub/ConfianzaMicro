package com.example.confianzamicro.repository

import com.example.confianzamicro.domain.AdvisorEntity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AdvisorRepository {

    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("advisors")

    // Insertar asesor
    suspend fun insertAdvisor(advisor: AdvisorEntity) {
        val data = hashMapOf(
            "username" to advisor.username,
            "password" to advisor.password
        )
        collection.add(data).await()
    }

    // Obtener todos los asesores
    suspend fun getAllAdvisors(): List<AdvisorEntity> {
        val snapshot = collection.get().await()
        return snapshot.documents.mapNotNull { doc ->
            val username = doc.getString("username")
            val password = doc.getString("password")
            if (username != null && password != null) {
                AdvisorEntity(
                    id = doc.id, // Firestore genera IDs string
                    username = username,
                    password = password
                )
            } else null
        }
    }

    // Eliminar asesor
    suspend fun deleteAdvisor(advisor: AdvisorEntity) {
        val snapshot = collection
            .whereEqualTo("username", advisor.username)
            .get()
            .await()

        for (doc in snapshot.documents) {
            doc.reference.delete().await()
        }
    }

    // Login de asesor
    suspend fun login(username: String, password: String): AdvisorEntity? {
        val snapshot = collection
            .whereEqualTo("username", username)
            .whereEqualTo("password", password)
            .get()
            .await()

        val doc = snapshot.documents.firstOrNull()
        return doc?.let {
            AdvisorEntity(
                id = it.id,
                username = it.getString("username") ?: "",
                password = it.getString("password") ?: ""
            )
        }
    }
}
