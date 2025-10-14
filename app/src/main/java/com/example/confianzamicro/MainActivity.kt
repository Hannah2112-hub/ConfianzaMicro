package com.example.confianzamicro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.confianzamicro.domain.AdvisorEntity
import com.example.confianzamicro.repository.AdvisorRepository
import com.example.confianzamicro.ui.theme.ConfianzaMicroTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Repositorio basado en Firestore
        val repository = AdvisorRepository()

        setContent {
            ConfianzaMicroTheme {
                LoginAndRegisterScreen(repository)
            }
        }
    }
}

@Composable
fun LoginAndRegisterScreen(repository: AdvisorRepository) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    var newUsername by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var showRegister by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Iniciar sesión", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Usuario") }
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") }
        )

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            scope.launch {
                val advisor = repository.login(username, password)
                message = if (advisor != null) {
                    "¡Bienvenido ${advisor.username}!"
                } else {
                    "Credenciales incorrectas"
                }
            }
        }) {
            Text("Ingresar")
        }

        Spacer(modifier = Modifier.height(8.dp))
        TextButton(onClick = { showRegister = !showRegister }) {
            Text(if (showRegister) "Cancelar registro" else "Registrar nuevo asesor")
        }

        if (showRegister) {
            Spacer(modifier = Modifier.height(16.dp))
            Text("Registrar Asesor", style = MaterialTheme.typography.titleMedium)
            OutlinedTextField(
                value = newUsername,
                onValueChange = { newUsername = it },
                label = { Text("Usuario") }
            )
            OutlinedTextField(
                value = newPassword,
                onValueChange = { newPassword = it },
                label = { Text("Contraseña") }
            )

            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {
                if (newUsername.isNotBlank() && newPassword.isNotBlank()) {
                    scope.launch {
                        repository.insertAdvisor(
                            AdvisorEntity(
                                username = newUsername,
                                password = newPassword
                            )
                        )
                        newUsername = ""
                        newPassword = ""
                        message = "Asesor registrado correctamente"
                    }
                }
            }) {
                Text("Guardar Asesor")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        if (message.isNotBlank()) {
            Text(message)
        }
    }
}
