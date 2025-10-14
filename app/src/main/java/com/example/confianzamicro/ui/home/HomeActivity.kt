package com.example.confianzamicro.ui.home

import android.content.Intent
import androidx.activity.ComponentActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.confianzamicro.auth.SessionManager
import com.example.confianzamicro.ui.auth.LoginActivity
import com.example.confianzamicro.ui.theme.ConfianzaMicroTheme

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConfianzaMicroTheme {
                HomeScreen()
            }
        }
    }
}

@Composable
fun HomeScreen() {
    val advisor = SessionManager.current()
    val context = LocalContext.current

    val welcomeText = advisor?.let { "Bienvenido, ${it.username}" } ?: "Sin sesión activa"

    Box(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = welcomeText, style = MaterialTheme.typography.titleLarge)

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                SessionManager.logout()
                context.startActivity(Intent(context, LoginActivity::class.java))
            }) {
                Text("Cerrar sesión")
            }
        }
    }
}
