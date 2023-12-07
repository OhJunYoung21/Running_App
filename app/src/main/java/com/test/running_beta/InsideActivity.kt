package com.test.running_beta

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.UiThread
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationServices
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.MapFragment
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.test.running_beta.databinding.ActivityInsideBinding

class InsideActivity : AppCompatActivity(),OnMapReadyCallback {

    private lateinit var naverMap: NaverMap

    private lateinit var binding: ActivityInsideBinding

    private lateinit var mapview:MapView


    var permissions = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInsideBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mapview = binding.mapView
        mapview.onCreate(savedInstanceState)
        mapview.getMapAsync(this)
        
    }

    override fun onMapReady(naverMap: NaverMap) {

        this.naverMap = naverMap

        var camPos = CameraPosition(
            LatLng(34.38, 128.55),
            9.0
        )
        naverMap.cameraPosition = camPos
    }

}
