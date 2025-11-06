package com.example.project1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.project1.databinding.MapBinding

class MapActivity : AppCompatActivity(){
    private lateinit var binding: MapBinding;
    var classMain = MainActivity();
    private var mainActivity = MainActivity();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        binding = MapBinding.inflate(layoutInflater);
        setContentView(binding.root);

        setSupportActionBar(binding.toolbar);
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setHomeButtonEnabled(true);
        supportActionBar?.title = "Stride&Dungeon";

        binding.backButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java));
        }
        binding.settingsButton.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> { // ← kliknięcie strzałki
                startActivity(Intent(this, MainActivity::class.java))
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}