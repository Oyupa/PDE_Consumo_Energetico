package com.example.pde_consumo_energetico

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore

class RecomendacionesActivity : ComponentActivity() {

    private var db: FirebaseFirestore = Firebase.firestore
    private lateinit var Button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recomendaciones)

        Button = findViewById(R.id.buttonVolver)
        Button.setOnClickListener {
            val intent = Intent(this, PantallaConsumoEnergeticoActivity::class.java)
            startActivity(intent)
        }
        obtenerDatosConsumo { consumoSemanaActual, consumoMedio ->
            val recomendacionConsumo = generarRecomendacion(consumoSemanaActual, consumoMedio)


            val textViewRecomendaciones = findViewById<TextView>(R.id.textViewRecomendaciones)
            textViewRecomendaciones.text = "$recomendacionConsumo"
        }

    }


    private fun obtenerDatosConsumo(callback: (Int, Int) -> Unit) {
        db.collection("consumo")
            .orderBy("semana", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val consumos = querySnapshot.documents

                if (consumos.size >= 2) {
                    val consumoSemanaActual = consumos[0].getLong("consumo")?.toInt() ?: 0
                    val consumoMedio = consumos.subList(1, consumos.size).map { it.getLong("consumo")?.toInt() ?: 0 }.average().toInt()
                    callback(consumoSemanaActual, consumoMedio)
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

    private fun generarRecomendacion(consumoSemanaActual: Int, consumoMedio: Int): String {
        return when {
            consumoSemanaActual < consumoMedio -> "Tu consumo medio es de $consumoMedio euros, y la última semana has consumido $consumoSemanaActual euros. ¡Sigue así!"
            consumoSemanaActual > consumoMedio -> "Tu consumo medio es de $consumoMedio euros, pero la última semana consumiste $consumoSemanaActual euros. Podrías reducir el gasto energético apagando luces y electrodomésticos cuando no los necesites."
            else -> "Tu consumo se ha mantenido estable en $consumoMedio euros. Recuerda que siempre puedes ahorrar energía apagando luces y electrodomésticos innecesarios."
        }
    }





}
