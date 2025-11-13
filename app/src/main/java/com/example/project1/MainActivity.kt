package com.example.project1


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.project1.databinding.ActivityMainBinding

open class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding.root);

        /* -----------  DOPASOWANIE DO STATUS BAR ----------- */
        ViewCompat.setOnApplyWindowInsetsListener(binding.toolbar) { view, insets ->
            val statusBarInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars());
            view.setPadding(view.paddingLeft, statusBarInsets.top, view.paddingRight, view.paddingBottom);
            insets;
        }

        /* -----------  Toolbar ----------- */
        setSupportActionBar(binding.toolbar);
        supportActionBar?.title = "Stride&Dungeon";




        /* ---------------- Button-------------------- */
        val dbHelper = DataBaseHelper(this)
        binding.welcomeButton.setOnClickListener {
            startActivity(Intent(this, MapActivity::class.java));
            val text = binding.inputText.text.toString()
            val change = binding.radioGroup.checkedRadioButtonId
            dbHelper.insertText(text, change)
        }


    }
}