package com.example.project1


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.project1.databinding.WelcomeScreenBinding


open class WelcomeScreen : AppCompatActivity() {
    private lateinit var binding: WelcomeScreenBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);


        binding = WelcomeScreenBinding.inflate(layoutInflater);
        setContentView(binding.root);

        ViewCompat.setOnApplyWindowInsetsListener(binding.toolbar) { view, insets ->
            val statusBarInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars());
            view.setPadding(view.paddingLeft, statusBarInsets.top, view.paddingRight, view.paddingBottom);
            insets;
        }

        binding.welcomeButton.setOnClickListener {
            startActivity(Intent(this, MapActivity::class.java));
        }


    }
}