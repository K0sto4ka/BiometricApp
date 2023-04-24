package com.magenta.biometricapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.magenta.biometricapp.databinding.ActivityMainBinding
import java.util.concurrent.Executor
import androidx.biometric.BiometricPrompt.PromptInfo

class MainActivity : AppCompatActivity() {
    lateinit var bind: ActivityMainBinding
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: PromptInfo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)

        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this@MainActivity,executor,
        object:BiometricPrompt.AuthenticationCallback() {

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                val text = "Ошибка авторизации $errString"
                bind.AusStatus.text = text
                showToast(text)
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                val text = "Сбой при авторизации "
                bind.AusStatus.text = text
                showToast(text)
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                val text = "Успешная авторизация "
                bind.AusStatus.text = text
                showToast(text)
            }
        })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Биометрическая авторизация")
            .setSubtitle("Вход с помощью отпечатка пальцев или лица")
            .setNegativeButtonText("Использовать пароль")
            .build()

        bind.btnAus.setOnClickListener {
        biometricPrompt.authenticate(promptInfo)
        }


    }
    private fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}