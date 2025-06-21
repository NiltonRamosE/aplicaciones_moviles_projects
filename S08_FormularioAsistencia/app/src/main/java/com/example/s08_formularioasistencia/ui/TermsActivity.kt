package com.example.s08_formularioasistencia.ui

import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.s08_formularioasistencia.R
import com.example.s08_formularioasistencia.databinding.ActivityTermsBinding

class TermsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTermsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTermsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupWebView(savedInstanceState)
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.terms_and_conditions)
    }

    private fun setupWebView(savedInstanceState: Bundle?) {
        binding.webView.apply {
            // Configuración básica
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true

            // Configuración de caché
            settings.cacheMode = WebSettings.LOAD_DEFAULT

            // Client personalizado
            webViewClient = object : WebViewClient() {
                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    super.onReceivedError(view, request, error)
                    Toast.makeText(
                        this@TermsActivity,
                        "Error al cargar la página. Verifique su conexión a internet",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            // Restaurar estado o cargar nueva URL
            if (savedInstanceState != null) {
                restoreState(savedInstanceState)
            } else {
                loadUrl("https://chimboteonline.com/San_Pedrito/")
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.webView.saveState(outState)
    }

    override fun onBackPressed() {
        if (binding.webView.canGoBack()) {
            binding.webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}