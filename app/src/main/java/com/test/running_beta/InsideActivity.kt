package com.test.running_beta

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.test.running_beta.databinding.ActivityInsideBinding

class InsideActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityInsideBinding

    var permissions = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInsideBinding.inflate(layoutInflater)

        setContentView(binding.root)

        if (isPermitted()) {
            finish()
        } else {
            ActivityCompat.requestPermissions(this, permissions, 523)
        }//권한 확인


    }

    //권한 승인이 이뤄졌는지를 확인하는 코드
    private fun isPermitted(): Boolean {

        for (p in permissions) {
            if (ContextCompat.checkSelfPermission(this, p) != PackageManager.PERMISSION_GRANTED) {

                return false
            }
        }
        return false
    }


    //지도 프로세스를 시작하는 코드
    fun startProcess(){
        val fm = supportFragmentManager
        val mapFragment = binding.mapFragment as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map_fragment,it).commit()
            } //권한
        mapFragment.getMapAsync(this)
    } //권한이 있다면 onMapReady연결

    override fun onMapReady(p0: NaverMap) {
        TODO("Not yet implemented")
    }
}