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
import com.example.confianzamicro.domain.ClientEntity
import com.example.confianzamicro.ui.theme.ConfianzaMicroTheme
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ClientsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConfianzaMicroTheme {
                ClientsScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientsScreen() {
    val db = FirebaseFirestore.getInstance()
    val coroutineScope = rememberCoroutineScope()
    var clients by remember { mutableStateOf(listOf<ClientEntity>()) }

    var name by remember { mutableStateOf("") }
    var dni by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    suspend fun loadClients() {
        val snapshot = db.collection("clients").get().await()
        clients = snapshot.documents.mapNotNull {
            it.toObject(ClientEntity::class.java)?.copy(id = it.id)
        }
    }

    LaunchedEffect(Unit) { loadClients() }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Gestión de Clientes (Firebase)") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nombre") })
            OutlinedTextField(value = dni, onValueChange = { dni = it }, label = { Text("DNI") })
            OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("Teléfono") })
            OutlinedTextField(value = address, onValueChange = { address = it }, label = { Text("Dirección") })

            Button(
                onClick = {
                    if (name.isNotBlank() && dni.isNotBlank()) {
                        coroutineScope.launch {
                            db.collection("clients").add(
                                mapOf(
                                    "name" to name,
                                    "dni" to dni,
                                    "phone" to phone,
                                    "address" to address
                                )
                            ).await()
                            name = ""; dni = ""; phone = ""; address = ""
                            loadClients()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
            ) {
                Text("Registrar Cliente")
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text("Lista de Clientes", style = MaterialTheme.typography.titleMedium)

            LazyColumn {
                items(clients) { client ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("👤 ${client.name}")
                                Text("🪪 DNI: ${client.dni}")
                                Text("📞 ${client.phone}")
                            }
                            IconButton(onClick = {
                                coroutineScope.launch {
                                    val snapshot = db.collection("clients")
                                        .whereEqualTo("dni", client.dni)
                                        .get().await()
                                    for (doc in snapshot.documents) {
                                        db.collection("clients").document(doc.id).delete().await()
                                    }
                                    loadClients()
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
