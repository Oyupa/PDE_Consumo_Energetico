package com.example.pde_consumo_energetico

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.firestore.FirebaseFirestore

class PantallaConsumoEnergeticoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            PantallaConsumoConFirebase(navController)
        }
    }
}

@Composable
fun PantallaConsumoEnergetico(
    consumoData: List<Consumo>,
    navigateToMenuPrincipal: () -> Unit,
    navigateToRecomendaciones: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Consumo Energético",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Llamada al gráfico de consumo
        LineChart(consumoData)

        Spacer(modifier = Modifier.height(24.dp))

        // Botón para volver al menú principal
        Button(
            onClick = navigateToMenuPrincipal,
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "Volver al Menú Principal")
        }

        // Botón para ir a recomendaciones
        Button(
            onClick = navigateToRecomendaciones,
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "Ver Recomendaciones de Ahorro")
        }
    }
}

@Composable
fun LineChart(consumoData: List<Consumo>) {
    // Dimensiones del gráfico
    val chartWidth = 300.dp
    val chartHeight = 200.dp
    val maxConsumo = consumoData.maxOfOrNull { it.consumo } ?: 1.0

    // Aumentar la altura del gráfico para acomodar las etiquetas
    val adjustedChartHeight = chartHeight - 60.dp // Espacio para la etiqueta Y y el eje X

    Canvas(modifier = Modifier.size(chartWidth, adjustedChartHeight)) {
        // Espaciado de la cuadrícula
        val gridSpacing = adjustedChartHeight.toPx() / 10
        val maxWeek = consumoData.size.toFloat()

        // Dibujar la cuadrícula
        for (i in 0..10) {
            val y = gridSpacing * i
            drawLine(
                color = Color.LightGray,
                start = Offset(0f, y),
                end = Offset(size.width, y),
                strokeWidth = 1f
            )
        }

        // Dibujar los ejes
        drawLine(
            color = Color.Black,
            start = Offset(0f, size.height),
            end = Offset(size.width, size.height),
            strokeWidth = 2f
        )

        drawLine(
            color = Color.Black,
            start = Offset(0f, 0f),
            end = Offset(0f, size.height),
            strokeWidth = 2f
        )

        // Dibujar números en el eje Y
        for (i in 0..10) {
            val y = size.height - (gridSpacing * i)
            val value = (maxConsumo / 10) * i
            drawContext.canvas.nativeCanvas.drawText(
                String.format("%.1f", value),
                5f,  // Posición X
                y - 5f,  // Posición Y (un poco arriba del eje)
                android.graphics.Paint().apply {
                    color = android.graphics.Color.BLACK
                    textSize = 20f
                }
            )
        }

        // Etiqueta del eje Y
        drawContext.canvas.nativeCanvas.drawText(
            "Consumo (kWh)",
            15f,
            20f, // Posición Y ajustada
            android.graphics.Paint().apply {
                color = android.graphics.Color.BLACK
                textSize = 24f
                textAlign = android.graphics.Paint.Align.LEFT
            }
        )

        // Dibujar números en el eje X
        for (i in 0 until consumoData.size) {
            val x = (i.toFloat() / (consumoData.size - 1).toFloat()) * size.width
            drawContext.canvas.nativeCanvas.drawText(
                "${consumoData[i].semana}",
                x - 10f,  // Centrar el texto
                size.height + 20f,  // Un poco abajo del eje (ajustado para evitar solapamiento)
                android.graphics.Paint().apply {
                    color = android.graphics.Color.BLACK
                    textSize = 20f
                }
            )
        }

        // Etiqueta del eje X
        drawContext.canvas.nativeCanvas.drawText(
            "Semana",
            size.width - 50f,
            size.height + 40f, // Espacio ajustado
            android.graphics.Paint().apply {
                color = android.graphics.Color.BLACK
                textSize = 24f
                textAlign = android.graphics.Paint.Align.LEFT
            }
        )

        // Líneas del gráfico
        val path = Path()

        consumoData.forEachIndexed { index, consumo ->
            val x = (index.toFloat() / (consumoData.size - 1).toFloat()) * size.width
            val y = size.height - ((consumo.consumo / maxConsumo).toFloat()) * size.height

            if (index == 0) {
                path.moveTo(x, y)
            } else {
                path.lineTo(x, y)
            }
        }

        // Dibujar la línea del gráfico
        drawPath(
            path = path,
            color = Color.Blue,
            style = Stroke(width = 5f, cap = StrokeCap.Round, join = StrokeJoin.Round)
        )

        // Dibujar los puntos del gráfico
        consumoData.forEachIndexed { index, consumo ->
            val x = (index.toFloat() / (consumoData.size - 1).toFloat()) * size.width
            val y = size.height - ((consumo.consumo / maxConsumo).toFloat()) * size.height
            drawCircle(
                color = Color.Red,
                radius = 6f,
                center = Offset(x, y)
            )
        }
    }
}

fun obtenerDatosDeFirebase(onDataLoaded: (List<Consumo>) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    db.collection("consumo_energetico")
        .get()
        .addOnSuccessListener { result ->
            val consumoData = result.map { document ->
                val dia = document.getLong("dia")?.toInt() ?: 0
                val consumo = document.getDouble("consumo")?.toFloat() ?: 0f
                Consumo(dia, consumo.toDouble())
            }
            onDataLoaded(consumoData)
        }
}

@Composable
fun PantallaConsumoConFirebase(
    navController: NavController
) {
    var consumoData by remember { mutableStateOf(emptyList<Consumo>()) }

    // Obtener los datos de Firebase al inicializar el Composable
    LaunchedEffect(Unit) {
        obtenerDatosDeFirebase { data ->
            consumoData = data
        }
    }

    // Mostrar la pantalla con los datos recuperados
    PantallaConsumoEnergetico(
        consumoData = consumoData,
        navigateToMenuPrincipal = { navController.navigate("menu") },
        navigateToRecomendaciones = { navController.navigate("recomendaciones") }
    )
}

@Composable
fun NavegacionApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "menu") {
        composable("menu") {
            // Iniciar SplashActivity directamente
            val context = LocalContext.current
            SplashActivity() // Cambiar esto para usar un Intent en lugar de un Composable
            context.startActivity(Intent(context, SplashActivity::class.java))
        }

        // Pantalla de Consumo
        composable("consumo") { PantallaConsumoConFirebase(navController) }

        // Pantalla de Recomendaciones
        composable("recomendaciones") {
            val context = LocalContext.current
            RecomendacionesActivity()
            context.startActivity(Intent(context, RecomendacionesActivity::class.java))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPantallaConsumoEnergetico() {
    val sampleData = listOf(
        Consumo(1, 23.79),
        Consumo(2, 135.0),
        Consumo(3, 36.85),
        Consumo(4, 100.60),
        Consumo(5, 15.87)
    )
    PantallaConsumoEnergetico(
            consumoData = sampleData,
            navigateToMenuPrincipal = {},
            navigateToRecomendaciones = {}
    )
}