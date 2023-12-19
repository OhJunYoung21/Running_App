package com.test.running_beta.UI

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.LocationOverlay
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import com.test.running_beta.R.id
import com.test.running_beta.databinding.ActivityRunBinding
import java.security.Permission

class RunActivity : AppCompatActivity(), OnMapReadyCallback {

    lateinit var binding: ActivityRunBinding

    lateinit var locationSource: FusedLocationSource

    private lateinit var naverMap: NaverMap

    lateinit var permission: Permission
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRunBinding.inflate(layoutInflater)

        val view = binding.root

        setContentView(view)

        val fm = supportFragmentManager

        /**Companion Object 로 정의된 permission_request_code 를 받아온다**/

        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)

        val mapFragment =
            fm.findFragmentById(id.map) as MapFragment? ?: MapFragment.newInstance()
                .also {
                    fm.beginTransaction().add(id.map, it).commit()
                }
        //getMapAsync는 onMapReady의 콜백함수를 실행한다.
        mapFragment.getMapAsync(this)


    }

    override fun onMapReady(naverMap: NaverMap) {

        naverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_BICYCLE, true)

        /**사용자 인터페이스 에서 UI 설정에 접근할 수 있다.**/

        this.naverMap = naverMap

        naverMap.locationSource = locationSource

        val uiSettings = naverMap.uiSettings

        /**좌측 하단에 있는 현위치 버튼을 활성화 시키는 코드**/

        uiSettings.isLocationButtonEnabled = true

        naverMap.locationTrackingMode = LocationTrackingMode.Follow

        val locationOverlay = naverMap.locationOverlay

        val currentLocation = locationSource.lastLocation

        currentLocation?.let{

            val currentLatLng = LatLng(it.latitude,it.longitude)

            locationOverlay.position = currentLatLng

        }

        locationOverlay.icon =
            OverlayImage.fromResource(com.naver.maps.map.R.drawable.navermap_location_overlay_icon)

        locationOverlay.iconWidth = LocationOverlay.SIZE_AUTO
        locationOverlay.iconHeight = LocationOverlay.SIZE_AUTO


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        /** FusedLocationSource 는 onRequestPermissionResult()메서드 를 interface 안에 가지고 있기에 사용 가능 하다.**/

        if (locationSource.onRequestPermissionsResult(
                requestCode, permissions,
                grantResults
            )
        ) {
            if (!locationSource.isActivated) { // 권한 거부됨
                naverMap.locationTrackingMode = LocationTrackingMode.None
            }
            return
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }
}