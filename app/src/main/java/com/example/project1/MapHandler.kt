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
import com.mapbox.geojson.Point
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import kotlin.random.Random
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import com.mapbox.maps.plugin.Plugin
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager

class MapHandler(
    private val context: Context,
    private val binding: ActivityMapBinding
) {

    fun initMap() {
        val mapboxMap = binding.mapView.getMapboxMap()

        mapboxMap.loadStyle(Style.MAPBOX_STREETS) {style ->

            style.addImage(
                "marker-icon",
                drawableToBitmap(R.drawable.enemy_foreground)!!
            )
            enableLocationPuck()
            addRandomMarkerNearUser()
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
        binding.mapView.location.enabled = true
        binding.mapView.location.puckBearingEnabled = true
        val mapviewViewport = binding.mapView.viewport
        val followPuckViewportState: FollowPuckViewportState =
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
        mapviewViewport.transitionTo(followPuckViewportState){succes ->

        }
    }

//    fun getUserLocation() {
//        val locationService: LocationService = LocationServiceFactory.getOrCreate()
//        var locationProvider: DeviceLocationProvider? = null
//        var final = "";
//
//        val request = LocationProviderRequest.Builder()
//            .interval(
//                IntervalSettings.Builder().interval(0L).minimumInterval(0L).maximumInterval(0L)
//                    .build()
//            )
//            .displacement(0F)
//            .accuracy(AccuracyLevel.HIGHEST)
//            .build();
//
//        val result = locationService.getDeviceLocationProvider(request)
//        if (result.isValue) {
//            locationProvider = result.value!!
//        } else {
//            Log.e("MapHandler", "Failed to get device location provider")
//        }
//        locationProvider?.getLastLocation { location ->
//            if (location != null) {
//                val lat = location.latitude
//                val lng = location.longitude
//                Log.d("MapHandler", "Lat: $lat, Lng: $lng")
//                final = "$lat,$lng"
//            } else {
//                Log.e("MapHandler", "Location is null")
//            }
//        }
//    }

    fun addRandomMarkerNearUser() {
        val locationService: LocationService = LocationServiceFactory.getOrCreate()

        val request = LocationProviderRequest.Builder()
            .accuracy(AccuracyLevel.HIGHEST)
            .build()

        val result = locationService.getDeviceLocationProvider(request)

        if (!result.isValue) {
            Log.e("MapHandler", "No location provider")
            return
        }

        val locationProvider = result.value!!

        locationProvider.getLastLocation { location ->
            if (location == null) {
                Log.e("MapHandler", "Location null")
                return@getLastLocation
            }

            val userLat = location.latitude
            val userLng = location.longitude

            // 🔥 Generate small random offset (~100–300 meters)
            val randomLatOffset = Random.nextDouble(-0.002, 0.002)
            val randomLngOffset = Random.nextDouble(-0.002, 0.002)

            val randomPoint = Point.fromLngLat(
                userLng + randomLngOffset,
                userLat + randomLatOffset
            )

            addMarker(randomPoint)
        }
    }
    private fun addMarker(point: Point) {

        val annotationApi = binding.mapView.annotations
        val pointAnnotationManager = annotationApi.createPointAnnotationManager()

        val pointAnnotationOptions = PointAnnotationOptions()
            .withPoint(point)
            .withTextField("Random marker")
            .withIconImage("marker-icon")

        pointAnnotationManager.create(pointAnnotationOptions)
    }

    private fun drawableToBitmap(drawableId: Int): Bitmap {
        val drawable = ContextCompat.getDrawable(context, drawableId)!!
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }
}
