package com.example.confianzamicro.ui.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.confianzamicro.auth.SessionManager
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
    // ✅ Usar SessionManager directamente, no get()
    val advisor = SessionManager.current()
    val welcomeText = advisor?.let { "Bienvenido, ${it.username}" } ?: "Sin sesión"

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = welcomeText, style = MaterialTheme.typography.titleLarge)
    }
}
