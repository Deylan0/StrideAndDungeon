package com.example.project1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.project1.databinding.MapBinding

class Map : AppCompatActivity() {
    private lateinit var binding: MapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title = "Stride&Dungeon"

        binding.backButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

    }
}