package com.example.confianzamicro

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.confianzamicro.ui.theme.ConfianzaMicroTheme
import com.example.confianzamicro.RetrofitInstance
import com.example.confianzamicro.Post
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.e("API", "MainActivity arrancó")
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ConfianzaMicroTheme {
                var texto by remember { mutableStateOf("Cargando...") }

                // Llamada inicial a la API
                LaunchedEffect(Unit) {
                    fetchPost { result -> texto = result }
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Surface(modifier = Modifier.padding(innerPadding)) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(texto)
                            Button(
                                onClick = { fetchPost { result -> texto = result } },
                                modifier = Modifier.padding(top = 12.dp)
                            ) {
                                Text("Recargar")
                            }
                        }
                    }
                }
            }
        }
    }

    private fun fetchPost(onResult: (String) -> Unit) {
        val call: Call<Post> = RetrofitInstance.api.getPost()
        call.enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (response.isSuccessful) {
                    val post = response.body()
                    val texto = post?.let { "Título: ${it.title}\n\n${it.body}" } ?: "Respuesta vacía"
                    Log.d("API", "Post recibido: $post")
                    runOnUiThread { onResult(texto) }
                } else {
                    Log.e("API", "Error: ${response.code()} ${response.message()}")
                    runOnUiThread { onResult("Error: ${response.code()}") }
                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                Log.e("API", "onFailure: ${t.message}", t)
                runOnUiThread { onResult("Fallo: ${t.localizedMessage}") }
            }
        })
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ConfianzaMicroTheme {
        Text("Vista previa de la API")
    }
}
