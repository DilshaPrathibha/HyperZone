package com.example.hyperzone.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hyperzone.R
import com.example.hyperzone.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            binding = ActivityLoginBinding.inflate(layoutInflater)
            setContentView(binding.root)
            
            // Delay setting up click listeners to ensure view binding is complete
            handler.postDelayed({
                setupClickListeners()
            }, 100)
            
        } catch (e: Exception) {
            Log.e("LoginActivity", "Error in onCreate", e)
            showError("An error occurred. Please restart the app.")
        }
    }

    private fun setupClickListeners() {
        try {
            binding.btnLogin.setOnClickListener {
                navigateToMainActivity()
            }
            
            // Set up clickable sign up text
            val signUpText = getString(R.string.dont_have_account)
            val signUpSpan = android.text.SpannableString(signUpText + " " + getString(R.string.sign_up))
            signUpSpan.setSpan(
                android.text.style.ForegroundColorSpan(getColor(R.color.primary)),
                signUpText.length + 1,
                signUpText.length + 1 + getString(R.string.sign_up).length,
                android.text.Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            binding.tvSignUpPrompt.text = signUpSpan
            
            // Make only the "Sign up" part clickable
            binding.tvSignUpPrompt.movementMethod = android.text.method.LinkMovementMethod.getInstance()
            binding.tvSignUpPrompt.setOnClickListener {
                startActivity(Intent(this, SignUpActivity::class.java))
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
            
        } catch (e: Exception) {
            Log.e("LoginActivity", "Error setting up click listeners", e)
            showError("Failed to initialize UI. Please restart the app.")
        }
    }
    
    private fun navigateToMainActivity() {
        try {
            val intent = Intent(this, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            startActivity(intent)
            // Don't call finish() here to allow proper back navigation if needed
        } catch (e: Exception) {
            Log.e("LoginActivity", "Error navigating to MainActivity", e)
            showError("Failed to login. Please try again.")
        }
    }
    
    private fun showError(message: String) {
        try {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Log.e("LoginActivity", "Error showing toast", e)
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}