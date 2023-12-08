package com.test.running_beta

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.annotation.UiThread
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.test.running_beta.databinding.ActivityInsideBinding

class InsideActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var naverMap: NaverMap

    private lateinit var binding: ActivityInsideBinding

    private lateinit var mapview: MapView

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient //자동으로 gps값을 받아온다.
    lateinit var locationCallback: LocationCallback //gps응답 값을 가져온다.
    //lateinit: 나중에 초기화 해주겠다는 의미

    //Android O.S에 현재 위치를 Request 하는 작업
    var permissions = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInsideBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mapview = binding.mapView
        //사용자 경험을 위해 만들어진 bundle 객체입니다. 만일
        mapview.getMapAsync(this)

        // 위치 권한이 승인된 경우, if 문 안의 명령문 수행
        if (checkLocationPermissions()) {
            initLocation()
        } else {
            //위치 권한이 승인 되지 않은 경우, 권한 요청 작업 수행(정상 작동 확인)
            requestLocationPermissions()
        }

    }

    override fun onMapReady(naverMap: NaverMap) {

        var camPos = CameraPosition(
            LatLng(10.38, 10.55),
            9.0
        )
        naverMap.cameraPosition = camPos
        this.naverMap = naverMap

    }

    //permission 배열안의 값들이 전부 승인되었다면 true를, 아니하면 false를 반환한다.
    private fun checkLocationPermissions(): Boolean {
        return permissions.all {
            ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }
    }

    //현재 위치를 요청한다.
    private fun requestLocationPermissions() {
        ActivityCompat.requestPermissions(this, permissions, 123)
    }

    @SuppressLint("MissingPermission")
    private fun initLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
            location?.let {
                // 위치를 지도의 초기 위치로 설정
                val camPos = CameraPosition(
                    LatLng(it.latitude, it.longitude),
                    9.0
                )

                naverMap.cameraPosition = camPos
            }
        }

        // 위치 변경 시마다 호출되는 콜백 등록
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult?.lastLocation?.let {
                    // 위치가 변경되면 지도의 카메라를 업데이트
                    val camUpdate = CameraUpdate.scrollTo(LatLng(it.latitude, it.longitude))
                    naverMap.moveCamera(camUpdate)
                }
            }
        }

        // 위치 업데이트를 요청
        startLocationUpdates()
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        val locationRequest = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(500)
            .setFastestInterval(200)

        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

}
