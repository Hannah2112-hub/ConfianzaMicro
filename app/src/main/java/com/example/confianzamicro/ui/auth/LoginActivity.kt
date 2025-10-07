package com.example.confianzamicro.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.confianzamicro.auth.SessionManager
import com.example.confianzamicro.data.db.AppDatabase
import com.example.confianzamicro.repository.AdvisorRepository
import com.example.confianzamicro.ui.clients.AdvisorsActivity
import com.example.confianzamicro.ui.theme.ConfianzaMicroTheme
import com.example.confianzamicro.viewmodel.AdvisorViewModel
import kotlinx.coroutines.launch
import androidx.room.Room
import com.example.confianzamicro.ui.menu.AdvisorMenuActivity

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Crear base de datos y repositorio
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "confianza_db"
        ).build()
        val repository = AdvisorRepository(db.advisorDao())

        setContent {
            ConfianzaMicroTheme {
                LoginScreen(repository) {
                    startActivity(Intent(this, AdvisorMenuActivity::class.java))
                    finish()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(repository: AdvisorRepository, onLoginSuccess: () -> Unit) {
    val viewModel: AdvisorViewModel = viewModel(factory = object : androidx.lifecycle.ViewModelProvider.Factory {
        override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
            return AdvisorViewModel(repository) as T
        }
    })

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Scaffold(
        topBar = { TopAppBar(title = { Text("Inicio de Sesión") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
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

            Spacer(modifier = Modifier.height(12.dp))

            // Botón para iniciar sesión
            Button(
                onClick = {
                    coroutineScope.launch {
                        val advisor = viewModel.login(username, password)
                        if (advisor != null) {
                            SessionManager.login(advisor) // Guardar sesión
                            message = "Bienvenido, ${advisor.username}"
                            onLoginSuccess()
                        } else {
                            message = "Credenciales incorrectas"
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Iniciar Sesión")
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Botón para registrar un nuevo asesor
            OutlinedButton(
                onClick = {
                    val intent = Intent(context, AdvisorsActivity::class.java)
                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrar Nuevo Asesor")
            }

            if (message.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
