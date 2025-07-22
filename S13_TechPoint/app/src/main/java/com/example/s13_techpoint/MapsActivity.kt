package com.example.s13_techpoint

import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityMapsBinding
    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var currentLocation: LatLng? = null
    private val computerStores = listOf(
        ComputerStore("TecnoShop", LatLng(-9.0745, -78.5936), "Av. Pardo 123"),
        ComputerStore("PC Express", LatLng(-9.0755, -78.5926), "Av. José Gálvez 456"),
        ComputerStore("CompuMundo", LatLng(-9.0735, -78.5946), "Calle Manuel Ruiz 789"),
        ComputerStore("TechCenter", LatLng(-9.0760, -78.5910), "Av. Bolognesi 321"),
        ComputerStore("Digital House", LatLng(-9.0720, -78.5950), "Jr. Salaverry 654")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar el mapa
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        binding.myLocationButton.setOnClickListener {
            currentLocation?.let {
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(it, 15f))
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        // Configuración inicial del mapa centrado en Chimbote
        val chimbote = LatLng(-9.0745, -78.5936)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(chimbote, 14f))

        // Solicitar permisos de ubicación
        requestLocationPermissions()

        // Configurar marcadores
        setupMarkers()
    }

    private fun requestLocationPermissions() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            map.isMyLocationEnabled = true
            getCurrentLocation()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            location?.let {
                currentLocation = LatLng(it.latitude, it.longitude)
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation!!, 15f))
                highlightNearbyStores()
            }
        }
    }

    private fun setupMarkers() {
        computerStores.forEach { store ->
            val marker = map.addMarker(
                MarkerOptions()
                    .position(store.location)
                    .title(store.name)
                    .snippet(store.address)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
            )
            marker?.tag = store
        }

        map.setOnMarkerClickListener { marker ->
            val store = marker.tag as? ComputerStore
            store?.let {
                marker.showInfoWindow()
                // Aquí podrías mostrar más detalles si lo deseas
            }
            true
        }
    }

    private fun highlightNearbyStores() {
        currentLocation?.let { myLocation ->
            // Ordenar tiendas por distancia
            val sortedStores = computerStores.sortedBy { store ->
                val results = FloatArray(1)
                Location.distanceBetween(
                    myLocation.latitude, myLocation.longitude,
                    store.location.latitude, store.location.longitude,
                    results
                )
                results[0]
            }

            // Resaltar las 3 más cercanas
            sortedStores.take(3).forEachIndexed { index, store ->
                map.addMarker(
                    MarkerOptions()
                        .position(store.location)
                        .title("${index + 1}. ${store.name}")
                        .snippet(store.address)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestLocationPermissions()
            }
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1001
    }
}

data class ComputerStore(val name: String, val location: LatLng, val address: String)