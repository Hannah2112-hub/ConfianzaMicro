package com.example.confianzamicro

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore

class ConfianzaApp : Application() {

    companion object {
        lateinit var firestore: FirebaseFirestore
    }

    override fun onCreate() {
        super.onCreate()

        // Inicializa Firebase
        FirebaseApp.initializeApp(this)

        // Instancia global de Firestore
        firestore = FirebaseFirestore.getInstance()
    }
}
