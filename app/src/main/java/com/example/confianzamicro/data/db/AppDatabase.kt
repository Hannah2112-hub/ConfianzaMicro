package com.example.confianzamicro.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.confianzamicro.domain.AdvisorEntity
import com.example.confianzamicro.domain.ClientEntity

@Database(
    entities = [AdvisorEntity::class, ClientEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun advisorDao(): AdvisorDao
    abstract fun clientDao(): ClientDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "confianza_db"
                )
                    .fallbackToDestructiveMigration() // ✅ esto evita errores si cambió el esquema
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
