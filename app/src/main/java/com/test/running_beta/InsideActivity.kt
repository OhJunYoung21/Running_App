package com.test.running_beta

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
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
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapFragment
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import com.test.running_beta.databinding.ActivityInsideBinding
import java.util.Timer
import kotlin.concurrent.timer

class InsideActivity : AppCompatActivity(), OnMapReadyCallback, View.OnClickListener {

    private var time = 0

    private lateinit var timer: Timer

    private var isRunning: Boolean = false

    private lateinit var naverMap: NaverMap

    private lateinit var binding: ActivityInsideBinding

    private lateinit var mapview: MapView

    private lateinit var locationSource: FusedLocationSource

    private val marker = Marker()

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

        binding.play.setOnClickListener(this)


        mapview = binding.mapView

        // 위치 권한이 승인된 경우, if 문 안의 명령문 수행
        if (checkLocationPermissions()) {
            //비동기적으로 onMapReady() 콜백함수를 실행하는 명령문
            mapview.getMapAsync(this)
        } else {
            //위치 권한이 승인 되지 않은 경우, 권한 요청 작업 수행(정상 작동 확인)
            requestLocationPermissions()
        }


    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.play -> {
                //현재 Timer 가 작동 중 이라면
                if (isRunning) {
                    pauseTimer()
                    //현재 Timer 가 작동 중이 아니라면
                } else {
                    startTimer()
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(naverMap: NaverMap) {

        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        // 위치 정보 요청
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
            location?.let {
                // 위치를 받아와서 지도의 초기 위치로 설정
                val camPos = CameraPosition(
                    LatLng(it.latitude, it.longitude),
                    9.0
                )

                marker.position = LatLng(it.latitude, it.longitude)

                marker.map = naverMap

                naverMap.cameraPosition = camPos
            }
        }
        naverMap.uiSettings.isLocationButtonEnabled = true

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

    private fun startTimer() {

        binding.sec.start()

        isRunning = true

        binding.play.setImageResource(R.drawable.pause)

    }

    private fun pauseTimer() {

        binding.sec.stop()

        isRunning = false

        binding.play.setImageResource(R.drawable.play)



    }


}
