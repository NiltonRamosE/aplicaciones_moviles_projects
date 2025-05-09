package com.example.s02_unsformregister

import android.content.Intent
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imageView = findViewById<ImageView>(R.id.bgImage)

        imageView.post {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val blur = RenderEffect.createBlurEffect(20f, 20f, Shader.TileMode.CLAMP)
                imageView.setRenderEffect(blur)
            }
        }


        val registerBtn = findViewById<Button>(R.id.register_btn)
        val viewRegistersBtn = findViewById<Button>(R.id.view_registers_btn)

        registerBtn.setOnClickListener {
            val intent = Intent(this@MainActivity, RegisterActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)
        }

        viewRegistersBtn.setOnClickListener {
            val intent = Intent(this@MainActivity, ViewStudentsActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)
        }
    }
}