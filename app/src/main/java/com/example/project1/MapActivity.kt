package com.example.project1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.project1.databinding.ActivityMapBinding
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import android.util.Log


class MapActivity : AppCompatActivity(), PermissionsListener {

    private lateinit var binding: ActivityMapBinding
    private lateinit var drawerToggle: ActionBarDrawerToggle
    private lateinit var permissionManager: PermissionsManager

    private val TAG: String = "MapActivity"


    private lateinit var mapHandler: MapHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mapHandler = MapHandler(this, binding)

        //  PERMISSIONS
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            mapHandler.initMap()
        } else {
            permissionManager = PermissionsManager(this)
            permissionManager.requestLocationPermissions(this)
        }

        //toolbar under status bar
        ViewCompat.setOnApplyWindowInsetsListener(binding.toolbar) { view, insets ->
            val statusBarInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            view.setPadding(
                view.paddingLeft,
                statusBarInsets.top,
                view.paddingRight,
                view.paddingBottom
            )
            insets
        }

        /*-------- DRAWER --------*/
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Stride&Dungeon"

        drawerToggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_go_back -> startActivity(Intent(this, WelcomeScreen::class.java))
                R.id.action_settings -> startActivity(Intent(this, SettingsActivity::class.java))
            }
            binding.drawerLayout.closeDrawers()
            true
        }
        binding.focusPuck.setOnClickListener { mapHandler.focusPuck() }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    override fun onPermissionResult(granted: Boolean) {
        if (granted) {
            mapHandler.onPermissionResult(granted)
        } else {
            Log.i(TAG, "permission denied")
        }
    }

    override fun onExplanationNeeded(permissionsToExplain: List<String>) {
        mapHandler.onExplanationNeeded(permissionsToExplain)
    }

}
