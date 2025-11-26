package com.example.project1

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.project1.databinding.ActivityMapBinding
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.createDefault2DPuck
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.viewport.viewport


class MapActivity : AppCompatActivity(), PermissionsListener{
    private lateinit var binding: ActivityMapBinding;
    lateinit var drawerToggle: ActionBarDrawerToggle;
    private lateinit var permissionManager: PermissionsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapBinding.inflate(layoutInflater);
        setContentView(binding.root);

        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            initMap()
        } else {
            permissionManager = PermissionsManager(this)
            permissionManager.requestLocationPermissions(this)
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.toolbar) { view, insets ->
            val statusBarInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars());
            view.setPadding(view.paddingLeft, statusBarInsets.top, view.paddingRight, view.paddingBottom);
            insets;
        }

        /*--------hamburger menu---------*/
        setSupportActionBar(binding.toolbar);
        supportActionBar?.title = "Stride&Dungeon";
        drawerToggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        /*----------Buttons handlin---------*/
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when(menuItem.itemId){
                R.id.action_go_back -> {
                    startActivity(Intent(this, WelcomeScreen::class.java))
                }
                R.id.action_settings -> {
                    startActivity(Intent(this, SettingsActivity::class.java))
                }
            }
            binding.drawerLayout.closeDrawers()
            true
        }
    }
    private fun initMap(){
        binding.mapView.gestures.pitchEnabled = true
        val mapboxMap = binding.mapView.getMapboxMap()

        mapboxMap.loadStyle(Style.MAPBOX_STREETS){ _ ->
            enableLocationPuck()
        }
    }

    private fun enableLocationPuck(){
        with(binding.mapView){
            location.locationPuck = createDefault2DPuck(withBearing = true)
            location.enabled = true

            viewport.transitionTo(
                targetState = viewport.makeFollowPuckViewportState(),
                transition = viewport.makeImmediateViewportTransition()
            )
        }
    }

    override fun onExplanationNeeded(permissionsToExplain: List<String>) {
        Toast.makeText(this, "Aplikacja potrzebuje lokalizacji, aby działać poprawnie.", Toast.LENGTH_LONG).show()
    }

    override fun onPermissionResult(granted: Boolean) {
        if (granted) {
            initMap()
        } else {
            Toast.makeText(this, "Odmówiono lokalizacji.", Toast.LENGTH_LONG).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}