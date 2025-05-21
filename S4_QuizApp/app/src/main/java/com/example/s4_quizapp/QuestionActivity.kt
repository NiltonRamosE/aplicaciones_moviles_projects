package com.example.s4_quizapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class QuestionActivity : AppCompatActivity() {

    data class Question(
        val text: String,
        val options: List<String>,
        val correctAnswer: String
    )

    private val questions = listOf(
        Question(
            "¿En qué carpeta se guardan las animaciones?",
            listOf(
                "/res/drawable",
                "/res/animate",
                "/res/src/anim",
                "/res/anim"
            ),
            "/res/anim"
        ),
        Question(
            "¿Qué componente de la interfaz nativa de Android se utiliza para mostrar una lista de elementos desplazable?",
            listOf("EditText", "ListView", "Button", "ImageView"),
            "ListView"
        ),
        Question(
            "¿Qué archivo en un proyecto Android define el nombre del paquete y las configuraciones del compilador?",
            listOf("AndroidManifest.xml", "build.gradle (Module:app)", "MainActivity.java", "build.gradle (Project)"),
            "build.gradle (Module:app)"
        ),
        Question(
            "¿Cuál de los siguientes ViewGroup organiza sus hijos en una sola fila o columna?",
            listOf("ConstraintLayout", "RelativeLayout", "LinearLayout", "FrameLayout"),
            "LinearLayout"
        ),
        Question(
            "¿Qué atributo en constraintlayout permite posicionar una vista a la abajo de otra por medio de un ID?",
            listOf("layout_constraintTop_toBottomOf", "layout_constraintStart_toStartOf", "layout_constraintEnd_toEndOf", "layout_marginBottom"),
            "layout_constraintTop_toBottomOf"
        ),
        Question(
            "¿Cuál es el propósito del archivo AndroidManifest.xml en una aplicación Android?",
            listOf(
                "Controlar el diseño visual de las actividades",
                "Definir permisos, actividades y servicios del proyecto",
                "Compilar el código fuente",
                "Almacenar los recursos de imagen"
            ),
            "Definir permisos, actividades y servicios del proyecto"
        ),
        Question(
            "¿Qué componente nativo se utiliza para permitir al usuario ingresar texto?",
            listOf("TextView", "EditText", "Button", "CheckBox"),
            "EditText"
        ),
        Question(
            "¿Cuál de las siguientes afirmaciones sobre ConstraintLayout es verdadera?",
            listOf(
                "Cada vista debe estar alineada a otra usando match_parent",
                "No se puede usar dentro de un ScrollView",
                "Se necesita al menos una restricción horizontal y una vertical para cada vista",
                "Es exclusivo para tablets y pantallas grandes"
            ),
            "Se necesita al menos una restricción horizontal y una vertical para cada vista"
        ),
        Question(
            "¿Qué herramienta dentro de Android Studio permite ver los logs de la aplicación?",
            listOf("Logcat", "Profiler", "Layout Inspector", "Design Editor"),
            "Logcat"
        ),
        Question(
            "En la estructura de un proyecto Android, ¿dónde se almacenan los archivos XML de diseño de interfaz?",
            listOf("/src/main/assets", "/res/layout", "/res/drawable", "/src/layout"),
            "/res/layout"
        )
    )

    private var currentIndex = 0
    private var score = 0
    private lateinit var restartBtn: Button
    private var username: String? = null

    private lateinit var questionText: TextView
    private lateinit var optionButtons: List<Button>
    private lateinit var questionCounter: TextView
    private lateinit var timerText: TextView
    private var startTime: Long = 0
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.question_activity)

        username = intent.getStringExtra("USERNAME")

        questionCounter = findViewById(R.id.questionCounter)

        timerText = findViewById(R.id.timerText)
        startTime = System.currentTimeMillis()

        val timerHandler = android.os.Handler()
        val timerRunnable = object : Runnable {
            override fun run() {
                val elapsedMillis = System.currentTimeMillis() - startTime
                val seconds = (elapsedMillis / 1000) % 60
                val minutes = (elapsedMillis / 1000) / 60
                timerText.text = String.format("%02d:%02d", minutes, seconds)
                timerHandler.postDelayed(this, 1000)
            }
        }
        timerHandler.post(timerRunnable)

        progressBar = findViewById(R.id.progressBar)
        progressBar.max = questions.size
        progressBar.progress = 0

        questionText = findViewById(R.id.questionText)
        optionButtons = listOf(
            findViewById(R.id.option1),
            findViewById(R.id.option2),
            findViewById(R.id.option3),
            findViewById(R.id.option4)
        )

        showQuestion(currentIndex)

        for (button in optionButtons) {
            button.setOnClickListener {
                checkAnswer(button.text.toString())
            }
        }

        restartBtn = findViewById(R.id.restartBtn)
        restartBtn.setOnClickListener {
            currentIndex = 0
            score = 0
            restartBtn.visibility = View.GONE
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun showQuestion(index: Int) {
        val q = questions[index]
        questionText.text = q.text
        questionCounter.text = "${index + 1} de ${questions.size}"

        questionCounter.text = "${index + 1} de ${questions.size}"
        progressBar.progress = index + 1

        optionButtons.forEachIndexed { i, button ->
            button.text = q.options[i]
            button.isEnabled = true
            button.visibility = View.VISIBLE
        }
    }

    private fun checkAnswer(selectedAnswer: String) {
        val correctAnswer = questions[currentIndex].correctAnswer

        optionButtons.forEach { it.isEnabled = false }

        optionButtons.forEach { button ->
            when {
                button.text == correctAnswer -> {
                    button.setBackgroundResource(R.drawable.option_correct)
                }
                button.text == selectedAnswer && selectedAnswer != correctAnswer -> {
                    button.setBackgroundResource(R.drawable.option_incorrect)
                }
                else -> {
                    button.setBackgroundResource(R.drawable.option_design)
                }
            }
        }

        if (selectedAnswer == correctAnswer) score++

        currentIndex++
        if (currentIndex < questions.size) {
            questionText.postDelayed({
                optionButtons.forEach {
                    it.setBackgroundResource(R.drawable.option_design)
                    it.isEnabled = true
                }
                showQuestion(currentIndex)
            }, 1500)
        } else {
            showResult()
        }
    }


    private fun showResult() {
        val totalMillis = System.currentTimeMillis() - startTime
        val seconds = (totalMillis / 1000) % 60
        val minutes = (totalMillis / 1000) / 60
        val finalTime = String.format("%02d:%02d", minutes, seconds)

        questionText.text = "¡Has terminado, $username!\nPuntaje: $score de ${questions.size}\nTiempo: $finalTime"

        optionButtons.forEach { it.visibility = View.GONE }
        restartBtn.visibility = View.VISIBLE
    }
}
