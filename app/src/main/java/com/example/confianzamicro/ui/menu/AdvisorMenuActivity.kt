package com.example.confianzamicro.ui.menu

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.confianzamicro.ui.clients.ClientsActivity
import com.example.confianzamicro.ui.tasks.TasksActivity
import com.example.confianzamicro.ui.theme.ConfianzaMicroTheme
import kotlin.reflect.KClass

class AdvisorMenuActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConfianzaMicroTheme {
                MenuScreen()
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MenuScreen() {
        val items = listOf(
            MenuOption("Clientes", android.R.drawable.ic_menu_agenda, ClientsActivity::class),
            MenuOption("Tareas", android.R.drawable.ic_menu_edit, TasksActivity::class),
            MenuOption("Cobranza", android.R.drawable.ic_menu_send, TasksActivity::class),
            MenuOption("Solicitudes", android.R.drawable.ic_menu_upload, TasksActivity::class),
            MenuOption("Reportes", android.R.drawable.ic_menu_view, TasksActivity::class),
            MenuOption("Configuración", android.R.drawable.ic_menu_manage, TasksActivity::class)
        )

        Scaffold(
            topBar = { TopAppBar(title = { Text("Menú Asesor") }) }
        ) { padding ->
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(items) { item ->
                    MenuCard(item) { targetClass ->
                        startActivity(Intent(this@AdvisorMenuActivity, targetClass.java))
                    }
                }
            }
        }
    }

    @Composable
    fun MenuCard(item: MenuOption, onClick: (KClass<*>) -> Unit) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .clickable { onClick(item.target) },
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(item.iconRes),
                    contentDescription = item.title,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(item.title, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
