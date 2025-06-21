package com.example.s09_preferenciasandroid

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

object ThemeUtils {
    fun applyTheme(context: Context, isDarkMode: Boolean, rootView: View) {
        val backgroundColor = if (isDarkMode) {
            context.getColor(android.R.color.darker_gray)
        } else {
            context.getColor(android.R.color.white)
        }

        val textColor = if (isDarkMode) {
            context.getColor(android.R.color.white)
        } else {
            context.getColor(android.R.color.black)
        }

        rootView.setBackgroundColor(backgroundColor)

        // Recorrer todas las vistas y cambiar el color del texto
        if (rootView is ViewGroup) {
            for (i in 0 until rootView.childCount) {
                val child = rootView.getChildAt(i)
                if (child is TextView) {
                    child.setTextColor(textColor)
                } else if (child is ViewGroup) {
                    applyTheme(context, isDarkMode, child)
                }
            }
        }
    }
}