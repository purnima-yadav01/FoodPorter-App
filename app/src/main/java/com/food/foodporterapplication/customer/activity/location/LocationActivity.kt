package com.food.foodporterapplication.customer.activity.location

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.food.foodporterapplication.R
import com.food.foodporterapplication.databinding.ActivityLocationBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class LocationActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityLocationBinding
    private var predictionsList: MutableList<AutocompletePrediction> = mutableListOf()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var autocompleteAdapter: AutocompleteAdapter
    private lateinit var placesClient: PlacesClient
    var bottomSheetDialogs: BottomSheetDialog? = null
    private lateinit var googleMap: GoogleMap
    private var selectedAddress: String = ""
    private lateinit var select: TextView
    private lateinit var currentLocationTxt: TextView

    companion object {
        var REQUEST_LOCATION_PERMISSION: Int = 1
    }

    private var searchLocation = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.backBtn.setOnClickListener {
            finish()
        }

        searchLocation = intent.getStringExtra("editAdd").toString()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, getString(R.string.google_key))
        }
        placesClient = Places.createClient(this)


        // Initialize FusedLocationClient to get current location

        // Set up the search view listener
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { getAutocompleteSuggestions(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    binding.recyclerViewResults.visibility = View.GONE
                    binding.searchIcon.visibility = RecyclerView.VISIBLE
                } else {
                    binding.searchIcon.visibility = RecyclerView.GONE
                    getAutocompleteSuggestions(newText)
                }
                return true
            }
        })


        binding.recyclerViewResults.layoutManager = LinearLayoutManager(this)
        autocompleteAdapter = AutocompleteAdapter(this, predictionsList) { prediction ->
            // Handle item click and zoom to the selected location
            getPlaceDetails(prediction.placeId)
        }
        binding.recyclerViewResults.adapter = autocompleteAdapter

        // Initialize map
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    private fun getAutocompleteSuggestions(query: String) {
        val token = AutocompleteSessionToken.newInstance()
        val request =
            FindAutocompletePredictionsRequest.builder().setSessionToken(token).setQuery(query)
                .build()

        placesClient.findAutocompletePredictions(request).addOnSuccessListener { response ->
            predictionsList.clear()
            predictionsList.addAll(response.autocompletePredictions)
            autocompleteAdapter.notifyDataSetChanged()
            if (predictionsList.isNotEmpty()) {
                binding.recyclerViewResults.visibility = View.VISIBLE
                binding.recycLinear.visibility = View.VISIBLE
            } else {
                binding.recyclerViewResults.visibility = View.GONE
                binding.recycLinear.visibility = View.GONE
            }
        }.addOnFailureListener { e ->
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }


    private fun getPlaceDetails(placeId: String) {
        val placeFields = listOf(Place.Field.ID, Place.Field.LAT_LNG, Place.Field.NAME)
        val request = FetchPlaceRequest.builder(placeId, placeFields).build()

        placesClient.fetchPlace(request).addOnSuccessListener { response ->
            val place = response.place
            val latLng = place.latLng
            if (latLng != null) {
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                googleMap.clear()  // Clear previous markers
                googleMap.addMarker(MarkerOptions().position(latLng).title(place.name))

                // 👉 Your required code block here:
                binding.recycLinear.visibility = View.GONE
                Log.e("recycLinearAAA", "recycLinear....")
                // Optional: Get address from LatLng
                getAddressFromLocation(latLng.latitude, latLng.longitude)
                changeAddressPopup()
            }
        }.addOnFailureListener { exception ->
            Log.e("LocationActivity", "Error fetching place details", exception)
        }
    }


    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        // Show current location on the map
        showCurrentLocation()

        // Add an on map click listener to handle user interaction
        googleMap.setOnMapClickListener { latLng ->
            googleMap.clear()
            val markerOptions = MarkerOptions().position(latLng)
            googleMap.addMarker(markerOptions)
            binding.recycLinear.visibility = View.GONE

            Log.e("recycLinearAAA", "recycLinear....")

            changeAddressPopup()
            getAddressFromLocation(latLng.latitude, latLng.longitude)
        }
    }

    private fun showCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    val latLng = LatLng(it.latitude, it.longitude)
                    googleMap.addMarker(MarkerOptions().position(latLng).title("Current Location"))
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))
                } ?: run {
                    Toast.makeText(this, "Unable to fetch location", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_PERMISSION
            )
        }
    }


    private fun getAddressFromLocation(latitude: Double, longitude: Double) {
        val geocoder = Geocoder(this, Locale.getDefault())
        val addresses: List<Address>? = geocoder.getFromLocation(latitude, longitude, 1)

        if (!addresses.isNullOrEmpty()) {
            val address = addresses[0]

            selectedAddress = address.getAddressLine(0) // Full address

            Log.e("selectedAddressAAA", "selectedAddress...$selectedAddress")

        } else {
            Toast.makeText(this, "Unable to fetch address", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showCurrentLocation()
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun cleanAddress(address: String?): String {
        if (address.isNullOrBlank()) return ""

        val parts = address.split(",").map { it.trim() }
        return if (parts.size > 2) {
            parts.dropLast(2).joinToString(", ")
        } else {
            address
        }
    }

    private fun changeAddressPopup() {
        bottomSheetDialogs = BottomSheetDialog(this, R.style.TopCircleDialogStyle)

        // Inflate the view
        val view = LayoutInflater.from(this).inflate(R.layout.change_add_popup_search, null)

        // Set 80% of screen height to the view
        val displayMetrics = resources.displayMetrics
        val screenHeight = displayMetrics.heightPixels
        val layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, (screenHeight * 0.3).toInt()
        )

        view.layoutParams = layoutParams

        bottomSheetDialogs!!.setContentView(view)

        // Show dialog
        bottomSheetDialogs!!.show()

        // Optional: Get BottomSheetBehavior and set peekHeight (in case you want collapsed behavior)
        val bottomSheet =
            bottomSheetDialogs!!.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheet?.let {
            val behavior = BottomSheetBehavior.from(it)
            behavior.peekHeight = (screenHeight * 0.8).toInt()
            behavior.state = BottomSheetBehavior.STATE_EXPANDED // Fully expand it
        }

        // Access views
        val dismissBtn = bottomSheetDialogs?.findViewById<ImageView>(R.id.dismissBtn)
        val confirmLocationBtn = bottomSheetDialogs?.findViewById<TextView>(R.id.confirmLocationBtn)
        currentLocationTxt = bottomSheetDialogs?.findViewById(R.id.currentLocationTxt)!!
        select = bottomSheetDialogs?.findViewById(R.id.select)!!

        // Dismiss dialog
        dismissBtn?.setOnClickListener {
            bottomSheetDialogs?.dismiss()
        }

        // Process selected address
        val parts = selectedAddress.split(",").map { it.trim() }

        val plusCode = if (parts.isNotEmpty()) parts[0] else ""
        val remainingAddress = if (parts.size > 2) {
            parts.drop(2).joinToString(", ")
        } else {
            parts.drop(1).joinToString(", ")
        }

        select?.text = plusCode
        currentLocationTxt?.text = remainingAddress

        // Confirm button action
        confirmLocationBtn?.setOnClickListener {
         //   addNewAddApi(selectedAddress)
        }
    }
}