package com.example.hyperzone.ui

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import com.example.hyperzone.databinding.ActivitySettingsBinding
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var sharedPrefs: android.content.SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupDarkModeSwitch()
        setupClickListeners()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupDarkModeSwitch() {
        sharedPrefs = getSharedPreferences("settings", Context.MODE_PRIVATE)
        val isDarkMode = sharedPrefs.getBoolean("dark_mode", false)
        binding.switchDark.isChecked = isDarkMode

        binding.switchDark.setOnCheckedChangeListener { _, isChecked ->
            sharedPrefs.edit {
                putBoolean("dark_mode", isChecked)
            }
            
            val mode = if (isChecked) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
            
            AppCompatDelegate.setDefaultNightMode(mode)
            recreate() // Optional: Apply theme immediately
        }
    }

    private fun setupClickListeners() {
        // Dummy listeners (UI only)
        binding.switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(this, "Notifications: $isChecked (demo)", Toast.LENGTH_SHORT).show()
        }

        binding.rowLanguage.setOnClickListener {
            Toast.makeText(this, "Language picker (demo)", Toast.LENGTH_SHORT).show()
        }

        binding.rowCurrency.setOnClickListener {
            Toast.makeText(this, "Currency picker (demo)", Toast.LENGTH_SHORT).show()
        }

        binding.rowClearCache.setOnClickListener {
            Toast.makeText(this, "Cache cleared (demo)", Toast.LENGTH_SHORT).show()
        }

        binding.rowAbout.setOnClickListener {
            Toast.makeText(this, "HyperZone • Demo settings", Toast.LENGTH_SHORT).show()
        }
    }
}