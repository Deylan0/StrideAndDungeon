package com.example.project1


import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.project1.databinding.ActivityMainBinding
import androidx.appcompat.app.ActionBarDrawerToggle
import android.widget.ArrayAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var drawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /* ----------- 1) DOPASOWANIE DO STATUS BAR (WindowInsets) ----------- */
        // to zapewnia, że toolbar nie będzie "pod" paskiem statusu na urządzeniach z notch / przezroczystym statusbarem
        ViewCompat.setOnApplyWindowInsetsListener(binding.toolbar) { view, insets ->
            val statusBarInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            // dodajemy górny padding równy wysokości statusbara
            view.setPadding(view.paddingLeft, statusBarInsets.top, view.paddingRight, view.paddingBottom)
            // zwracamy insets bez modyfikacji
            insets
        }

        /* ----------- 2) Toolbar jako ActionBar (bez findViewById) ----------- */
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title = "Stride&Dungeon"

        /* ----------- 3) Drawer (lista) ----------- */



        /* ----------------4) Button-------------------- */
        binding.welcomeButton.setOnClickListener {
            startActivity(Intent(this, Map::class.java))
        }


    }
}