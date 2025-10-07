package com.example.confianzamicro.ui.clients

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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.example.confianzamicro.data.db.AppDatabase
import com.example.confianzamicro.domain.AdvisorEntity
import com.example.confianzamicro.repository.AdvisorRepository
import com.example.confianzamicro.ui.theme.ConfianzaMicroTheme
import com.example.confianzamicro.viewmodel.AdvisorViewModel
import kotlinx.coroutines.launch

class AdvisorsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Crear base de datos
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "confianza_db"
        ).build()

        val repository = AdvisorRepository(db.advisorDao())

        setContent {
            ConfianzaMicroTheme {
                AdvisorScreen(repository)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdvisorScreen(repository: AdvisorRepository) {
    val viewModel: AdvisorViewModel = viewModel(factory = object : androidx.lifecycle.ViewModelProvider.Factory {
        override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
            return AdvisorViewModel(repository) as T
        }
    })

    val advisors by viewModel.advisors.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.loadAdvisors()
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Gestión de Asesores") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Formulario de registro
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
                            viewModel.addAdvisor(
                                AdvisorEntity(
                                    username = username,
                                    password = password
                                )
                            )
                            username = ""
                            password = ""
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Text("Registrar Asesor")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Lista de Asesores",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Lista de asesores
            LazyColumn {
                items(advisors) { advisor ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("👤 ${advisor.username}")
                                Text("🆔 ${advisor.id}")
                            }
                            IconButton(onClick = {
                                coroutineScope.launch {
                                    viewModel.deleteAdvisor(advisor)
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Eliminar"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
