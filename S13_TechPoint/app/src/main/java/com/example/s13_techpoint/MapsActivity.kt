package com.example.s13_techpoint

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.s13_techpoint.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar

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
    private lateinit var storesAdapter: ArrayAdapter<String>
    private var selectedStore: ComputerStore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar el mapa
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Configurar la lista de tiendas
        setupStoresList()

        binding.myLocationButton.setOnClickListener {
            currentLocation?.let {
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(it, 15f))
                showNearestStore()
            } ?: run {
                getCurrentLocation()
            }
        }

        binding.findNearestButton.setOnClickListener {
            if (currentLocation != null) {
                showNearestStore()
            } else {
                Snackbar.make(binding.root, "Obteniendo ubicación...", Snackbar.LENGTH_SHORT).show()
                getCurrentLocation()
            }
        }
    }

    private fun setupStoresList() {
        storesAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            computerStores.map { it.name }
        )
        storesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.storeSpinner.adapter = storesAdapter
        binding.storeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedStore = computerStores[position]
                selectedStore?.let {
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(it.location, 16f))
                    highlightStore(it)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun highlightStore(store: ComputerStore) {
        map.clear()
        setupMarkers()

        map.addMarker(
            MarkerOptions()
                .position(store.location)
                .title(store.name)
                .snippet(store.address)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
        )?.showInfoWindow()
    }

    private fun showNearestStore() {
        currentLocation?.let { myLocation ->
            val nearestStore = computerStores.minByOrNull { store ->
                calculateDistance(myLocation, store.location)
            }

            nearestStore?.let {
                val distance = String.format("%.2f km", calculateDistance(myLocation, it.location) / 1000)

                AlertDialog.Builder(this)
                    .setTitle("Tienda más cercana")
                    .setMessage("${it.name}\nDistancia: $distance\n\n${it.address}")
                    .setPositiveButton("Ir") { _, _ ->
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(it.location, 16f))
                        highlightStore(it)
                        selectStoreInSpinner(it)
                    }
                    .setNegativeButton("Cerrar", null)
                    .show()
            }
        } ?: run {
            Snackbar.make(binding.root, "No se pudo determinar tu ubicación", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun selectStoreInSpinner(store: ComputerStore) {
        val position = computerStores.indexOfFirst { it.name == store.name }
        if (position != -1) {
            binding.storeSpinner.setSelection(position)
        }
    }

    private fun calculateDistance(point1: LatLng, point2: LatLng): Float {
        val results = FloatArray(1)
        Location.distanceBetween(
            point1.latitude, point1.longitude,
            point2.latitude, point2.longitude,
            results
        )
        return results[0]
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
                showNearestStore()
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
                selectStoreInSpinner(it)
            }
            true
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
            } else {
                Snackbar.make(binding.root, "Permiso de ubicación denegado", Snackbar.LENGTH_LONG)
                    .setAction("Configuración") {
                        openAppSettings()
                    }
                    .show()
            }
        }
    }

    private fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = android.net.Uri.fromParts("package", packageName, null)
        }
        startActivity(intent)
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1001
    }
}

data class ComputerStore(val name: String, val location: LatLng, val address: String)