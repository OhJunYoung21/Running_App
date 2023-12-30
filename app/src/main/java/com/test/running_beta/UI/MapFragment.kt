package com.test.running_beta.UI

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.MarkerOptions
import com.test.running_beta.databinding.MapfragmentBinding

class MapFragment : Fragment(), OnMapReadyCallback {

    private var fusedLocationClient: FusedLocationProviderClient? = null

    private lateinit var mapView: MapView

    private lateinit var binding: MapfragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        binding = MapfragmentBinding.inflate(layoutInflater)

        mapView = binding.mapView

        mapView.onCreate(savedInstanceState)

        mapView.getMapAsync(this)

        return binding.root
    }

    override fun onMapReady(p0: GoogleMap) {



        if (ContextCompat.checkSelfPermission(
                requireContext(),
                ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            p0.isMyLocationEnabled = true
            fusedLocationClient?.lastLocation
                ?.addOnSuccessListener { it ->
                    if (it != null) {
                        val markerOptions = MarkerOptions()
                            .position(
                                com.google.android.gms.maps.model.LatLng(
                                    it.latitude,
                                    it.longitude
                                )
                            )
                            .title("Marker Title")

                        val marker = p0.addMarker(markerOptions)

// 마커가 보이도록 지도 이동
                        p0.moveCamera(CameraUpdateFactory.newLatLngZoom(marker!!.position, 15f))
                    }
                }

        }

    }

}