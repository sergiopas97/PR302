package com.example.pr302

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pr302.ui.theme.PR302Theme
import kotlinx.coroutines.delay
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PR302Theme (darkTheme = false) {
                MainScreen()

            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    var dineroInput by remember { mutableStateOf("") }
    var saldo by remember { mutableIntStateOf(0) }

    var saldoVisible by remember { mutableStateOf(false) }

    // Estado para controlar la visibilidad de la Snackbar
    var snackbarVisible by remember { mutableStateOf(false) }

    // Mensaje a mostrar en la Snackbar
    var snackbarMessage by remember { mutableStateOf("") }

    // Columna que organiza los elementos de la interfaz de usuario verticalmente
    Column(modifier = modifier.padding(16.dp)) {
        Text("Introduce la cantidad:")

        // Campo de texto para introducir la cantidad
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = dineroInput,
            onValueChange = {
                if (it.length < 5)
                    dineroInput = it
            },
        )

        // Fila que contiene botones para realizar operaciones
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            // Botón para añadir dinero
            Button(onClick = {
                val dinero = dineroInput.toIntOrNull() ?: 0
                saldo += dinero
                dineroInput = ""

                // Mostrar Snackbar con mensaje de confirmación
                snackbarMessage = "Se añadieron $dinero € a la cuenta"
                snackbarVisible = true
            }) {
                Text(text = "Añadir")
            }

            Spacer(modifier = Modifier.padding(horizontal = 5.dp))

            // Botón para sacar dinero
            Button(onClick = {
                val dinero = dineroInput.toIntOrNull() ?: 0
                saldo -= dinero
                dineroInput = ""

                // Mostrar Snackbar con mensaje de confirmación
                snackbarMessage = "Se retiraron $dinero € de la cuenta"
                snackbarVisible = true
            }) {
                Text(text = "Sacar")
            }

            Spacer(modifier = Modifier.padding(horizontal = 5.dp))

            // Botón para ver el saldo
            Button(onClick = {
                if (saldoVisible) {
                    saldoVisible = false
                } else {
                    // Lógica para mostrar el saldo actual
                    snackbarMessage = "Saldo actual: $saldo €"
                    snackbarVisible = true
                    saldoVisible = true
                }
            }) {
                Text(text = if (saldoVisible) "Ocultar saldo" else "Ver saldo")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Muestra información sobre el saldo de la cuenta (solo si saldoVisible es true)
        if (saldoVisible) {
            Text("Saldo de la cuenta: $saldo €")
        }

        // Snackbar para mostrar mensajes informativos
        LaunchedEffect(snackbarVisible) {
            if (snackbarVisible) {
                delay(1000) // Espera 1 segundo
                snackbarVisible = false
            }
        }

        if (snackbarVisible) {
            Snackbar(
                modifier = Modifier.padding(46.dp)
            ) {
                Text(text = snackbarMessage)
            }
        }
    }
}


// Composable para la vista previa de la interfaz de usuario
@Preview(showBackground = true, widthDp = 360)
@Composable
fun MyPreview() {
    PR302Theme(dynamicColor = false) {
        MainScreen(Modifier.fillMaxSize())
    }
}



