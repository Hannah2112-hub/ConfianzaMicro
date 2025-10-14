package com.example.confianzamicro.repository

import com.example.confianzamicro.domain.ClientEntity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ClientRepository {

    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("clients")

    // 🔹 Obtener todos los clientes
    suspend fun getClients(): List<ClientEntity> {
        val snapshot = collection.get().await()
        return snapshot.documents.mapNotNull { doc ->
            doc.toObject(ClientEntity::class.java)?.copy(id = doc.id)
        }
    }

    // 🔹 Agregar cliente
    suspend fun addClient(client: ClientEntity) {
        val data = hashMapOf(
            "name" to client.name,
            "dni" to client.dni,
            "phone" to client.phone,
            "address" to client.address
        )
        collection.add(data).await()
    }

    // 🔹 Eliminar cliente
    suspend fun deleteClient(client: ClientEntity) {
        // Buscamos el documento por su contenido (puedes cambiar la lógica si usas un campo único como DNI)
        val query = collection.whereEqualTo("dni", client.dni).get().await()
        for (doc in query.documents) {
            collection.document(doc.id).delete().await()
        }
    }
}
