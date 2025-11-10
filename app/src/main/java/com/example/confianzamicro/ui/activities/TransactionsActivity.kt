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
import com.example.confianzamicro.domain.TransactionEntity
import com.example.confianzamicro.repository.TransactionRepository
import com.example.confianzamicro.ui.theme.ConfianzaMicroTheme
import kotlinx.coroutines.launch

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
    val repository = remember { TransactionRepository() }
    val coroutineScope = rememberCoroutineScope()
    var transactions by remember { mutableStateOf(listOf<TransactionEntity>()) }

    var idClient by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    suspend fun loadTransactions() {
        transactions = repository.getTransactions()
    }

    LaunchedEffect(Unit) { loadTransactions() }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Gestión de Transacciones") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(value = idClient, onValueChange = { idClient = it }, label = { Text("Cliente") })
            OutlinedTextField(value = amount, onValueChange = { amount = it }, label = { Text("Monto") })
            OutlinedTextField(value = date, onValueChange = { date = it }, label = { Text("Fecha (YYYY-MM-DD)") })
            OutlinedTextField(value = type, onValueChange = { type = it }, label = { Text("Tipo (préstamo/pago)") })
            OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Descripción") })

            Button(
                onClick = {
                    if (idClient.isNotBlank() && amount.isNotBlank()) {
                        coroutineScope.launch {
                            repository.addTransaction(
                                TransactionEntity(
                                    idClient = idClient,
                                    amount = amount.toDoubleOrNull() ?: 0.0,
                                    date = date,
                                    type = type,
                                    description = description
                                )
                            )
                            idClient = ""; amount = ""; date = ""; type = ""; description = ""
                            loadTransactions()
                        }
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
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("👤 ${transaction.idClient}")
                                Text("💰 S/.${transaction.amount}")
                                Text("📅 ${transaction.date}")
                                Text("🔹 ${transaction.type}")
                            }
                            IconButton(onClick = {
                                coroutineScope.launch {
                                    repository.deleteTransaction(transaction)
                                    loadTransactions()
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