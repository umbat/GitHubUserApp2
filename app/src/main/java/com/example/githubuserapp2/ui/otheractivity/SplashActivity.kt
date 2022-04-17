package com.example.githubuserapp2.ui.otheractivity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.githubuserapp2.R
import com.example.githubuserapp2.main.MainActivity
import com.example.githubuserapp2.ui.darkmode.DarkModeViewModel
import com.example.githubuserapp2.ui.darkmode.SettingPreferences
import com.example.githubuserapp2.ui.darkmode.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val TIMEOUT = 1000L

        Handler(mainLooper).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, TIMEOUT)

        val pref = SettingPreferences.getInstance(dataStore)
        val darkModeViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            DarkModeViewModel::class.java
        )

        darkModeViewModel.getThemeSettings().observe(this
        ) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}