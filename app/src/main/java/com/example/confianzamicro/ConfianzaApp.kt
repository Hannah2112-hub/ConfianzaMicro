package com.example.confianzamicro

import android.app.Application
import androidx.room.Room
import com.example.confianzamicro.data.db.AppDatabase
import com.example.confianzamicro.domain.AdvisorEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ConfianzaApp : Application() {

    companion object {
        lateinit var database: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "confianza_db"
        ).build()

        // Insertar asesor por defecto si no existe
        CoroutineScope(Dispatchers.IO).launch {
            val advisors = database.advisorDao().getAllAdvisors().first()
            if (advisors.isEmpty()) {
                val defaultAdvisor = AdvisorEntity(
                    username = "admin",
                    password = "1234"
                )
                database.advisorDao().insertAdvisor(defaultAdvisor)
            }
        }
    }
}
