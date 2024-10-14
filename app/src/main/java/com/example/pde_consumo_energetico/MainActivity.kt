package com.example.pde_consumo_energetico

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.pde_consumo_energetico.ui.theme.PDE_Consumo_EnergeticoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencia a los botones
        val btnViewConsumption = findViewById<Button>(R.id.btnViewConsumption)
        val btnDataUpdate = findViewById<Button>(R.id.btnDataUpdate)

        // Acciones al hacer clic en los botones
        btnViewConsumption.setOnClickListener {
            // Aquí irá la acción para navegar a la pantalla de consumo energético
        }

        btnDataUpdate.setOnClickListener {
            // Aquí irá la acción para navegar a la pantalla de actualización de datos
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PDE_Consumo_EnergeticoTheme {
        Greeting("Android")
    }
}