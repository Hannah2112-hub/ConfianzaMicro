package com.example.confianzamicro.repository

import com.example.confianzamicro.domain.TransactionEntity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class TransactionRepository {

    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("transactions")

    // 🔹 Obtener todas las transacciones
    suspend fun getTransactions(): List<TransactionEntity> {
        val snapshot = collection.get().await()
        return snapshot.documents.mapNotNull { doc ->
            doc.toObject(TransactionEntity::class.java)?.copy(id = doc.id)
        }
    }

    // 🔹 Agregar una nueva transacción
    suspend fun addTransaction(transaction: TransactionEntity) {
        val data = hashMapOf(
            "idClient" to transaction.idClient,
            "amount" to transaction.amount,
            "date" to transaction.date,
            "type" to transaction.type,
            "description" to transaction.description
        )
        collection.add(data).await()
    }

    // 🔹 Eliminar transacción
    suspend fun deleteTransaction(transaction: TransactionEntity) {
        val query = collection.whereEqualTo("idClient", transaction.idClient)
            .whereEqualTo("date", transaction.date)
            .get()
            .await()

        for (doc in query.documents) {
            collection.document(doc.id).delete().await()
        }
    }
}