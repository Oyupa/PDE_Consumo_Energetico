package com.example.pde_consumo_energetico

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class NuevosDatos: ComponentActivity(){
    private lateinit var btnActualizar: Button
    private lateinit var btnCancelar: Button
    private lateinit var editSemana: EditText
    private lateinit var editConsumo: EditText
    private var db: FirebaseFirestore = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nuevos_datos) //asignamos el correspodinete layput

        btnActualizar = findViewById(R.id.btnActualizar)
        btnCancelar = findViewById(R.id.btnCancelar)
        editSemana = findViewById(R.id.editSemana)
        editConsumo = findViewById(R.id.editConsumo)

        btnActualizar.setOnClickListener {
            actualizar()
            finish()
            //boton encargado de guardar una novela nueva
        }

        btnCancelar.setOnClickListener {
            finish()
            //vuelve a la actividad inicial
        }

    }

    private fun actualizar() {
        val semana = editSemana.text.toString().toInt()
        val consumo = editConsumo.text.toString().toInt()
        val nuevosDatos = Consumo(semana, consumo)

        val consumoCollection = db.collection("consumo")

        consumoCollection
            .whereEqualTo("semana", semana)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    consumoCollection
                        .add(nuevosDatos)
                        .addOnSuccessListener { documentReference ->
                            Toast.makeText(this, "Datos de la semana ${nuevosDatos.semana} añadidos", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "Error al añadir los datos: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    for (document in documents) {
                        consumoCollection
                            .document(document.id)
                            .set(nuevosDatos)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Datos de la semana ${nuevosDatos.semana} actualizados", Toast.LENGTH_SHORT).show()
                                finish()
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(this, "Error al actualizar los datos: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                    }
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error al verificar la existencia de la semana: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }



}