package com.example.s12_calculadora

class CalculatorModel {
    fun calculate(firstOperand: Double, secondOperand: Double, operator: String): Double {
        return when (operator) {
            "+" -> firstOperand + secondOperand
            "-" -> firstOperand - secondOperand
            "×" -> firstOperand * secondOperand
            "÷" -> if (secondOperand != 0.0) firstOperand / secondOperand else Double.NaN
            "^" -> Math.pow(firstOperand, secondOperand)
            else -> Double.NaN
        }
    }

    fun calculateUnaryOperation(operand: Double, operator: String): Double {
        return when (operator) {
            "√" -> Math.sqrt(operand)
            else -> Double.NaN
        }
    }
}