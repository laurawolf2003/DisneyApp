package com.example.disney

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.disney.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            // NavHostFragment und NavController initialisieren
            val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            navController = navHostFragment.navController

            // Prüfen, ob der NavController erfolgreich initialisiert wurde
            Log.d("MainActivity", "NavController erfolgreich initialisiert")

            // ActionBar mit NavController verbinden für Titel und Back-Button
            setupActionBarWithNavController(navController)
        } catch (e: Exception) {
            // Fehlerbehandlung für die Navigation hinzugefügt
            Log.e("MainActivity", "Fehler bei der Initialisierung des NavControllers", e)
        }
    }

    // Unterstützung für Navigation Up-Button
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}