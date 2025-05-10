package com.example.s03_mothersletter

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.Gravity
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var heartLayer: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        heartLayer = findViewById(R.id.heartAnimationLayer)
        val btn = findViewById<Button>(R.id.btnMensaje)

        // Configurar el botón
        btn.setOnClickListener {
            showToast()
            vibrate()
            createFloatingHearts(10)
        }
    }

    // Muestra un Toast con el mensaje
    private fun showToast() {
        val toast = Toast.makeText(this, "¡Un abrazo gigante, mamá! ❤️", Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER, 0, 200)
        toast.show()
    }

    // Realiza la vibración del dispositivo
    private fun vibrate() {
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val vibrationEffect =
                VibrationEffect.createOneShot(400, VibrationEffect.DEFAULT_AMPLITUDE)
            vibrator.vibrate(vibrationEffect)
        } else {
            vibrator.vibrate(400)  // Para versiones anteriores
        }
    }

    // Crea los corazones flotantes desde la parte inferior
    private fun createFloatingHearts(count: Int) {
        val handler = Handler(Looper.getMainLooper())

        // Genera múltiples corazones
        repeat(count) { i ->
            handler.postDelayed({
                val heart = ImageView(this).apply {
                    setImageResource(R.drawable.heart)
                    layoutParams = FrameLayout.LayoutParams(60, 60).apply {
                        // Generar posiciones aleatorias para los corazones
                        val randomX = Random.nextInt(0, resources.displayMetrics.widthPixels - 60)
                        val randomY = resources.displayMetrics.heightPixels + 100 // Justo fuera de la pantalla
                        leftMargin = randomX
                        topMargin = randomY
                    }
                    alpha = 0.8f
                }

                heartLayer.addView(heart)
                animateHeart(heart)
            }, (i * 150).toLong())  // Retardo entre la aparición de cada corazón
        }
    }

    // Anima el corazón para que flote hacia arriba
    private fun animateHeart(heart: ImageView) {
        val anim = ObjectAnimator.ofPropertyValuesHolder(
            heart,
            PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, 0f, -1000f),  // Movimiento hacia arriba
            PropertyValuesHolder.ofFloat(View.ALPHA, 0.8f, 0f),  // Fade out
            PropertyValuesHolder.ofFloat(View.SCALE_X, 1f, 1.5f),  // Crecer en el eje X
            PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f, 1.5f)   // Crecer en el eje Y
        ).apply {
            duration = 2000  // Duración de la animación
            interpolator = AccelerateInterpolator()  // Aceleración en la animación
            start()
        }

    }
}
