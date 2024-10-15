package com.example.pde_consumo_energetico

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.ProgressBar
import androidx.activity.ComponentActivity

class SplashActivity : ComponentActivity() {
    private lateinit var progressBar: ProgressBar
    private var progressStatus = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        progressBar = findViewById(R.id.loadingBattery)

        // Iniciar el progreso
        Thread {
            while (progressStatus < 100) {
                progressStatus += 1
                // Actualiza el progreso en el hilo principal
                runOnUiThread {
                    progressBar.progress = progressStatus
                }
                try {
                    // Simula un tiempo de carga
                    Thread.sleep(20) // Ajusta el tiempo según sea necesario
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }

            // Navega a MainActivity una vez que el progreso esté completo
            runOnUiThread {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish() // Finaliza SplashActivity
            }
        }.start()
    }
}
