package com.example.s12_calculadora

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel() {
    private val model = CalculatorModel()

    private val _currentInput = MutableLiveData<String>().apply { value = "0" }
    val currentInput: LiveData<String> = _currentInput

    private val _operationDisplay = MutableLiveData<String>().apply { value = "" }
    val operationDisplay: LiveData<String> = _operationDisplay

    private var firstOperand: Double? = null
    private var currentOperator: String? = null
    private var resetInput = false

    fun onNumberClicked(number: String) {
        if (resetInput || _currentInput.value == "0") {
            _currentInput.value = number
            resetInput = false
        } else {
            _currentInput.value += number
        }
    }

    fun onDecimalClicked() {
        if (resetInput) {
            _currentInput.value = "0."
            resetInput = false
        } else if (!_currentInput.value!!.contains(".")) {
            _currentInput.value += "."
        }
    }

    fun onOperatorClicked(operator: String) {
        if (currentOperator != null && !resetInput) {
            calculateResult()
        }

        firstOperand = _currentInput.value!!.toDouble()
        currentOperator = operator
        _operationDisplay.value = "${formatNumber(firstOperand!!)} $operator"
        resetInput = true
    }

    fun onUnaryOperatorClicked(operator: String) {
        val result = model.calculateUnaryOperation(_currentInput.value!!.toDouble(), operator)
        if (!result.isNaN()) {
            _currentInput.value = formatNumber(result)
            _operationDisplay.value = "$operator(${formatNumber(_currentInput.value!!.toDouble())})"
        }
    }

    fun onEqualsClicked() {
        if (firstOperand != null && currentOperator != null) {
            calculateResult()
            currentOperator = null
        }
    }

    fun onClearClicked() {
        _currentInput.value = "0"
        _operationDisplay.value = ""
        firstOperand = null
        currentOperator = null
    }

    fun onNegateClicked() {
        val current = _currentInput.value!!.toDouble()
        _currentInput.value = formatNumber(-current)
    }

    private fun calculateResult() {
        if (firstOperand != null && currentOperator != null) {
            val secondOperand = _currentInput.value!!.toDouble()
            val result = model.calculate(firstOperand!!, secondOperand, currentOperator!!)

            if (!result.isNaN()) {
                _operationDisplay.value = "${formatNumber(firstOperand!!)} $currentOperator ${formatNumber(secondOperand)} ="
                _currentInput.value = formatNumber(result)
                resetInput = true
            } else {
                _currentInput.value = "Error"
                _operationDisplay.value = ""
            }
        }
    }

    private fun formatNumber(number: Double): String {
        return if (number % 1 == 0.0) {
            number.toInt().toString()
        } else {
            number.toString()
        }
    }
}