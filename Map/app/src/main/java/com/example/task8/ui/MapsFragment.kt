package com.example.task8.ui

import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.task8.R
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MapsFragment : Fragment(),
    GoogleMap.OnMarkerClickListener {


    private lateinit var map: GoogleMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location

    private lateinit var locationCallback: LocationCallback

    private lateinit var locationRequest: LocationRequest
    private var locationUpdateState = false

    /**
     * Instantiate Firebase instance
     */
    val db = FirebaseDatabase.getInstance("https://location-map-29215-default-rtdb.firebaseio.com")


    private val LOCATION_PERMISSION_REQUEST_CODE = 1
    private val REQUEST_CHECK_SETTINGS = 2

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */

        map = googleMap
        map.uiSettings.isZoomControlsEnabled = true
        map.setOnMarkerClickListener(this)
        setUpMap()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_maps, container, false)

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                lastLocation = p0.lastLocation

                /**
                 * Gets my last location and sends it to the Firebase realtime database
                 * so my partner can update his accordingly
                 */
                val currentLatLong = LatLng(lastLocation.latitude, lastLocation.longitude)
                db.getReference("Austine").setValue(currentLatLong)
            }
        }
        createLocationRequest()
    }

    private fun setUpMap() {

        /**
         * Checks if the app has been granted the ACCESS_FINE_LOCATION permission.
         * If it hasn’t, then request it from the user.
         */
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }
        map.isMyLocationEnabled = true
        var getData = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {

                var partnerLatLong = LatLng(
                    p0.child("lat").value as Double,
                    p0.child("long").value as Double
                )
                Log.i("Tag", "${p0.child("lat").value as Double}")

                placeMarkerOnMap(partnerLatLong)
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(partnerLatLong, 20f))

            }

        }
        db.getReference("kings").addValueEventListener(getData)
    }

    /**
     * Define a custom marker and add it to the map
     */
    private fun placeMarkerOnMap(location: LatLng) {
        val h = 100
        val w = 100
        val b = BitmapFactory.decodeResource(
            resources,
            R.drawable.kings
        )
        val smallMarker = Bitmap.createScaledBitmap(b, w, h, false)
        val smallMarkerIcon = BitmapDescriptorFactory.fromBitmap(smallMarker)

        val markerOptions = MarkerOptions().position(location)
        markerOptions.icon(
            smallMarkerIcon
        )
        markerOptions.title("Kingsley at Lat: ${location.latitude} and Long: ${location.longitude}")
        map.clear()
        map.addMarker(markerOptions)
    }

    /**
     * If the ACCESS_FINE_LOCATION permission has not been granted, request it now and return.
     * If there is permission, request for location updates.
     */
    private fun startLocationUpdates() {
        //1
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            null /* Looper */
        )
    }

    /**
     * Retrieve and handle any changes to be made based on the current state of the user’s location settings.
     */
    private fun createLocationRequest() {
        // 1
        locationRequest = LocationRequest()
        // 2
        locationRequest.interval = 4000
        // 3
        locationRequest.fastestInterval = 2000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
        // 4
        val client = LocationServices.getSettingsClient(requireContext())
        val task = client.checkLocationSettings(builder.build())
        // 5
        task.addOnSuccessListener {
            locationUpdateState = true
            startLocationUpdates()
        }
        task.addOnFailureListener { e ->
            // 6
            if (e is ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    e.startResolutionForResult(
                        requireActivity(),
                        REQUEST_CHECK_SETTINGS
                    )
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }
    }

    /**
     * Stop location update request when the app enters a paused state
     */
    override fun onPause() {
        super.onPause()
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    /**
     * Restart location update request when the app resumes
     */
    override fun onResume() {
        super.onResume()
        if (!locationUpdateState) {
            startLocationUpdates()
        }
    }

    override fun onMarkerClick(p0: Marker?) = false

}