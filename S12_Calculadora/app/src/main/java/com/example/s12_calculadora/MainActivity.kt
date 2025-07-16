package com.example.s12_calculadora

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private val viewModel: CalculatorViewModel by viewModels()

    // Declaración de views
    private lateinit var tvResult: TextView
    private lateinit var tvOperation: TextView
    private lateinit var btnZero: Button
    private lateinit var btnOne: Button
    private lateinit var btnTwo: Button
    private lateinit var btnThree: Button
    private lateinit var btnFour: Button
    private lateinit var btnFive: Button
    private lateinit var btnSix: Button
    private lateinit var btnSeven: Button
    private lateinit var btnEight: Button
    private lateinit var btnNine: Button
    private lateinit var btnDecimal: Button
    private lateinit var btnAdd: Button
    private lateinit var btnSubtract: Button
    private lateinit var btnMultiply: Button
    private lateinit var btnDivide: Button
    private lateinit var btnPower: Button
    private lateinit var btnEquals: Button
    private lateinit var btnSqrt: Button
    private lateinit var btnClear: Button
    private lateinit var btnNegate: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeViews();
        setupObservers()
        setupClickListeners()
    }

    private fun initializeViews() {
        tvResult = findViewById(R.id.tv_result)
        tvOperation = findViewById(R.id.tv_operation)
        btnZero = findViewById(R.id.btn_zero)
        btnOne = findViewById(R.id.btn_one)
        btnTwo = findViewById(R.id.btn_two)
        btnThree = findViewById(R.id.btn_three)
        btnFour = findViewById(R.id.btn_four)
        btnFive = findViewById(R.id.btn_five)
        btnSix = findViewById(R.id.btn_six)
        btnSeven = findViewById(R.id.btn_seven)
        btnEight = findViewById(R.id.btn_eight)
        btnNine = findViewById(R.id.btn_nine)
        btnDecimal = findViewById(R.id.btn_decimal)
        btnAdd = findViewById(R.id.btn_add)
        btnSubtract = findViewById(R.id.btn_subtract)
        btnMultiply = findViewById(R.id.btn_multiply)
        btnDivide = findViewById(R.id.btn_divide)
        btnPower = findViewById(R.id.btn_power)
        btnEquals = findViewById(R.id.btn_equals)
        btnSqrt = findViewById(R.id.btn_sqrt)
        btnClear = findViewById(R.id.btn_clear)
        btnNegate = findViewById(R.id.btn_negate)
    }
    private fun setupObservers() {
        viewModel.currentInput.observe(this, Observer { value ->
            tvResult.text = value
        })

        viewModel.operationDisplay.observe(this, Observer { operation ->
            tvOperation.text = operation
            // Limpiar después de 3 segundos si muestra un resultado
            if (operation.endsWith("=")) {
                tvOperation.postDelayed({ tvOperation.text = "" }, 3000)
            }
        })
    }

    private fun setupClickListeners() {
        // Números
        btnZero.setOnClickListener { viewModel.onNumberClicked("0") }
        btnOne.setOnClickListener { viewModel.onNumberClicked("1") }
        btnTwo.setOnClickListener { viewModel.onNumberClicked("2") }
        btnThree.setOnClickListener { viewModel.onNumberClicked("3") }
        btnFour.setOnClickListener { viewModel.onNumberClicked("4") }
        btnFive.setOnClickListener { viewModel.onNumberClicked("5") }
        btnSix.setOnClickListener { viewModel.onNumberClicked("6") }
        btnSeven.setOnClickListener { viewModel.onNumberClicked("7") }
        btnEight.setOnClickListener { viewModel.onNumberClicked("8") }
        btnNine.setOnClickListener { viewModel.onNumberClicked("9") }
        btnDecimal.setOnClickListener { viewModel.onDecimalClicked() }

        // Operadores básicos
        btnAdd.setOnClickListener { viewModel.onOperatorClicked("+") }
        btnSubtract.setOnClickListener { viewModel.onOperatorClicked("-") }
        btnMultiply.setOnClickListener { viewModel.onOperatorClicked("×") }
        btnDivide.setOnClickListener { viewModel.onOperatorClicked("÷") }
        btnPower.setOnClickListener { viewModel.onOperatorClicked("^") }
        btnEquals.setOnClickListener { viewModel.onEqualsClicked() }

        // Funciones especiales
        btnSqrt.setOnClickListener { viewModel.onUnaryOperatorClicked("√") }
        btnClear.setOnClickListener { viewModel.onClearClicked() }
        btnNegate.setOnClickListener { viewModel.onNegateClicked() }
    }
}