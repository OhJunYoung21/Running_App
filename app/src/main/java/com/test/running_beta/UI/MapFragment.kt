package com.test.running_beta.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.naver.maps.geometry.LatLng
import com.test.running_beta.databinding.MapfragmentBinding

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mapView: MapView

    private lateinit var binding: MapfragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = MapfragmentBinding.inflate(layoutInflater)

        mapView = binding.mapView

        mapView.onCreate(savedInstanceState)

        mapView.getMapAsync(this)

        return binding.root
    }

    override fun onMapReady(p0: GoogleMap) {
        val marker = LatLng(37.568291,126.997780)
    }
}