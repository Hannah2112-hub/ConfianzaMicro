package com.example.confianzamicro.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.confianzamicro.domain.AdvisorEntity
import com.example.confianzamicro.ui.theme.ConfianzaMicroTheme
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.launch

class AdvisorsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConfianzaMicroTheme {
                AdvisorScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdvisorScreen() {
    val db = FirebaseFirestore.getInstance()
    val coroutineScope = rememberCoroutineScope()

    var advisors by remember { mutableStateOf(listOf<AdvisorEntity>()) }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // 🔄 Cargar asesores desde Firestore
    suspend fun loadAdvisors() {
        val snapshot = db.collection("advisors").get().await()
        advisors = snapshot.documents.mapNotNull {
            it.toObject(AdvisorEntity::class.java)?.copy(id = it.id)
        }
    }

    LaunchedEffect(Unit) { loadAdvisors() }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Gestión de Asesores (Firebase)") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Usuario") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    if (username.isNotBlank() && password.isNotBlank()) {
                        coroutineScope.launch {
                            db.collection("advisors")
                                .add(mapOf("username" to username, "password" to password))
                                .await()
                            username = ""
                            password = ""
                            loadAdvisors()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
            ) {
                Text("Registrar Asesor")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Lista de Asesores", style = MaterialTheme.typography.titleMedium)

            LazyColumn {
                items(advisors) { advisor ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("👤 ${advisor.username}")
                                Text("🆔 ${advisor.id}")
                            }
                            IconButton(onClick = {
                                coroutineScope.launch {
                                    val snapshot = db.collection("advisors")
                                        .whereEqualTo("username", advisor.username)
                                        .get().await()
                                    for (doc in snapshot.documents) {
                                        db.collection("advisors").document(doc.id).delete().await()
                                    }
                                    loadAdvisors()
                                }
                            }) {
                                Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                            }
                        }
                    }
                }
            }
        }
    }
}
