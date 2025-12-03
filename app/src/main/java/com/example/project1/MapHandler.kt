package com.example.project1

import android.content.Context
import com.example.project1.databinding.ActivityMapBinding
import android.widget.Toast
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.locationcomponent.createDefault2DPuck
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.viewport.viewport
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.plugin.viewport.data.FollowPuckViewportStateBearing
import com.mapbox.maps.plugin.viewport.data.FollowPuckViewportStateOptions
import com.mapbox.maps.plugin.viewport.state.FollowPuckViewportState
import com.mapbox.common.location.LocationService
import com.mapbox.common.location.LocationServiceFactory
import com.mapbox.common.location.DeviceLocationProvider
import com.mapbox.common.location.LocationProviderRequest
import com.mapbox.common.location.IntervalSettings
import com.mapbox.common.location.AccuracyLevel
import android.util.Log

class MapHandler(
    private val context: Context,
    private val binding: ActivityMapBinding
) {

    fun initMap() {
        val mapboxMap = binding.mapView.getMapboxMap()

        mapboxMap.loadStyle(Style.MAPBOX_STREETS) {
            enableLocationPuck()
        }
    }

    private fun enableLocationPuck() {
        with(binding.mapView) {
            location.locationPuck = createDefault2DPuck(withBearing = true)
            location.enabled = true

            viewport.transitionTo(
                targetState = viewport.makeFollowPuckViewportState(),
                transition = viewport.makeImmediateViewportTransition()
            )
        }
    }

    fun onExplanationNeeded(permissionsToExplain: List<String>) {
        Toast.makeText(
            context,
            "Aplikacja potrzebuje lokalizacji, aby działać poprawnie.",
            Toast.LENGTH_LONG
        ).show()
    }

    fun onPermissionResult(granted: Boolean) {
        if (granted) {
            initMap()
        } else {
            Toast.makeText(context, "Odmówiono lokalizacji.", Toast.LENGTH_LONG).show()
        }
    }
    fun focusPuck() {
            val mapviewViewport = binding.mapView.viewport
            val followPuck: FollowPuckViewportState =
                mapviewViewport.makeFollowPuckViewportState(
                    FollowPuckViewportStateOptions.Builder()
                        .bearing(FollowPuckViewportStateBearing.Constant(0.0))
                        .padding(
                            EdgeInsets(
                                200.0 * context.resources.displayMetrics.density,
                                0.0,
                                0.0,
                                0.0
                            )
                        )
                        .build()
                )
    }

    fun getUserLocation() {
        val locationService: LocationService = LocationServiceFactory.getOrCreate()
        var locationProvider: DeviceLocationProvider? = null
        var final = "";

        val request = LocationProviderRequest.Builder()
            .interval(
                IntervalSettings.Builder().interval(0L).minimumInterval(0L).maximumInterval(0L)
                    .build()
            )
            .displacement(0F)
            .accuracy(AccuracyLevel.HIGHEST)
            .build();

        val result = locationService.getDeviceLocationProvider(request)
        if (result.isValue) {
            locationProvider = result.value!!
        } else {
            Log.e("MapHandler", "Failed to get device location provider")
        }
        locationProvider?.getLastLocation { location ->
            if (location != null) {
                val lat = location.latitude
                val lng = location.longitude
                Log.d("MapHandler", "Lat: $lat, Lng: $lng")
                final = "$lat,$lng"
            } else {
                Log.e("MapHandler", "Location is null")
            }
        }
    }
}
