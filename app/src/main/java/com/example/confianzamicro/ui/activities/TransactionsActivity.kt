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
import com.example.confianzamicro.ui.theme.ConfianzaMicroTheme

data class Transaction(
    val id: String,
    val clientName: String,
    val amount: String,
    val date: String,
    val status: String
)

class TransactionsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConfianzaMicroTheme {
                TransactionsScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionsScreen() {
    var transactions by remember { mutableStateOf(listOf<Transaction>()) }

    var clientName by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Gestión de Transacciones") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Campos de registro
            OutlinedTextField(value = clientName, onValueChange = { clientName = it }, label = { Text("Cliente") })
            OutlinedTextField(value = amount, onValueChange = { amount = it }, label = { Text("Monto") })
            OutlinedTextField(value = date, onValueChange = { date = it }, label = { Text("Fecha de Pago (dd/mm/aaaa)") })
            OutlinedTextField(value = status, onValueChange = { status = it }, label = { Text("Estado (Pendiente/Pagado)") })

            Button(
                onClick = {
                    if (clientName.isNotBlank() && amount.isNotBlank()) {
                        val newTransaction = Transaction(
                            id = System.currentTimeMillis().toString(),
                            clientName = clientName,
                            amount = amount,
                            date = date,
                            status = status
                        )
                        transactions = transactions + newTransaction
                        clientName = ""; amount = ""; date = ""; status = ""
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
            ) {
                Text("Registrar Transacción")
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text("Lista de Transacciones", style = MaterialTheme.typography.titleMedium)

            LazyColumn {
                items(transactions) { transaction ->
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
                                Text("👤 Cliente: ${transaction.clientName}")
                                Text("💰 Monto: ${transaction.amount}")
                                Text("📅 Fecha: ${transaction.date}")
                                Text("📌 Estado: ${transaction.status}")
                            }
                            IconButton(onClick = {
                                transactions = transactions.filter { it.id != transaction.id }
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
