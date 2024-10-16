package com.example.pde_consumo_energetico

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore

class RecomendacionesActivity : ComponentActivity() {
    private var db: FirebaseFirestore = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recomendaciones)

        val textViewRecomendaciones = findViewById<TextView>(R.id.textViewRecomendaciones)

        obtenerDatosConsumo { consumoSemanaActual, consumoSemanaAnterior ->
            val recomendacionConsumo = generarRecomendacion(consumoSemanaActual, consumoSemanaAnterior)
            val recomendacionEstacional = generarRecomendacionesEstacionales()

            textViewRecomendaciones.text = "$recomendacionConsumo\n\n$recomendacionEstacional"
        }

    }

    private fun obtenerDatosConsumo(callback: (Int, Int) -> Unit) {
        db.collection("consumo")
            .orderBy("semana", Query.Direction.DESCENDING)
            .limit(2)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val consumos = querySnapshot.documents

                if (consumos.size >= 2) {
                    val consumoSemanaActual = consumos[0].getLong("consumo")?.toInt() ?: 0
                    val consumoSemanaAnterior = consumos[1].getLong("consumo")?.toInt() ?: 0
                    callback(consumoSemanaActual, consumoSemanaAnterior)
                } else if (consumos.size == 1) {
                    val consumoSemanaActual = consumos[0].getLong("consumo")?.toInt() ?: 0
                    callback(consumoSemanaActual, 0)
                } else {
                    callback(0, 0)
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error al obtener los datos de consumo", Toast.LENGTH_SHORT).show()
                callback(0, 0)
            }
    }

        private fun generarRecomendacion(
            consumoSemanaActual: Int,
            consumoSemanaAnterior: Int
        ): String {
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
