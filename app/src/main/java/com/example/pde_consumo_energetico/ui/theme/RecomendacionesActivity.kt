package com.example.pde_consumo_energetico

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity

class RecomendacionesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recomendaciones)


        val consumoSemanaActual = 150 //Ejemplo: Datos simulados
        val consumoSemanaAnterior = 180


        val textViewRecomendaciones = findViewById<TextView>(R.id.textViewRecomendaciones)


        val recomendacionConsumo = generarRecomendacion(consumoSemanaActual, consumoSemanaAnterior)


        val recomendacionEstacional = generarRecomendacionesEstacionales()


        textViewRecomendaciones.text = "$recomendacionConsumo\n\n$recomendacionEstacional"
    }


    private fun generarRecomendacion(consumoSemanaActual: Int, consumoSemanaAnterior: Int): String {
        return when {
            consumoSemanaActual < consumoSemanaAnterior -> "¡Felicidades! Has reducido tu consumo en la última semana. Sigue aplicando buenas prácticas como el uso de luces LED y el aprovechamiento de la luz natural."
            consumoSemanaActual > consumoSemanaAnterior -> "Tu consumo ha aumentado en la última semana. Revisa si hay electrodomésticos en mal estado o si has aumentado el uso de calefacción o aire acondicionado. Considera mejorar la eficiencia energética de tu hogar."
            else -> "Tu consumo se ha mantenido estable. Continúa aplicando buenas prácticas de ahorro energético."
        }
    }


    private fun generarRecomendacionesEstacionales(): String {
        val month = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH)
        return when (month) {
            in 11..2 -> "Con el frío, el uso de calefacción puede aumentar. Mantén una temperatura moderada en tu hogar y asegúrate de que no haya fugas de aire en ventanas o puertas."
            in 5..8 -> "En días calurosos, evita el uso excesivo de aire acondicionado. Mantén las ventanas cerradas y usa ventiladores cuando sea posible para ahorrar energía."
            else -> "Mantén un uso balanceado de la energía durante las estaciones intermedias. Aprovecha la luz natural y ventila tu hogar de forma adecuada."
        }
    }
}
