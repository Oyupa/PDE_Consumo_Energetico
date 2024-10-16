package com.example.pde_consumo_energetico

import android.content.Intent
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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.pde_consumo_energetico.ui.theme.PDE_Consumo_EnergeticoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val btnViewConsumption = findViewById<Button>(R.id.btnViewConsumption)
        val btnDataUpdate = findViewById<Button>(R.id.btnDataUpdate)

        btnViewConsumption.setOnClickListener {
            val intent = Intent(this, PantallaConsumoEnergeticoActivity::class.java)
            startActivity(intent)
        }

        btnDataUpdate.setOnClickListener {
            val intent = Intent(this, NuevosDatos::class.java)
            startActivity(intent)
        }

    }
}
