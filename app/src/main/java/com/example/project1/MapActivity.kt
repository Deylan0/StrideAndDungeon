package com.example.project1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.project1.databinding.ActivityMapBinding

class MapActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMapBinding;
    var classMain = MainActivity();
    private var mainActivity = MainActivity();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapBinding.inflate(layoutInflater);
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

        val dbHelper = DataBaseHelper(this)
        val textt = dbHelper.getText()
        if (textt != null) {
            val text = textt.text
            val change = textt.change
            var changedText = ""
            if(change == 1){
                changedText = text.reversed()
            }else if (change == 2){
                changedText = text.uppercase()
            }else if(change == 3){
                changedText = text.length.toString()
            }else{
                changedText = "error in choice"
            }
            binding.outputText.text = changedText;
        }


    }
    /* ---- back button on toolbar ---- */
    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                startActivity(Intent(this, MainActivity::class.java))
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


}