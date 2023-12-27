package com.test.running_beta

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.test.running_beta.UI.MapFragment
import com.test.running_beta.UI.RunBottomSheetFragment
import com.test.running_beta.databinding.ActivityRunBinding

class RunActivity : AppCompatActivity() {


    lateinit var binding: ActivityRunBinding

    private lateinit var sheet: BottomSheetDialogFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRunBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        ActivityCompat.requestPermissions(this,arrayOf(ACCESS_FINE_LOCATION), requestCode)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.setIcon(R.drawable.arrow_button)

        supportActionBar?.setTitle("")



        val mapFragment = MapFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.mapFragmentContainer, mapFragment)
            .commit()


        binding.showRoute.setOnClickListener {

            sheet = RunBottomSheetFragment(this)

            sheet.show(supportFragmentManager, sheet.tag)


        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }

            else -> {}
        }
        return super.onOptionsItemSelected(item)
    }

    private fun checkPermission(){

        val locationPermission = ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION)

        if(locationPermission == PackageManager.PERMISSION_GRANTED){

            Toast.makeText(this,"권한이 승인되었습니다.",Toast.LENGTH_SHORT).show()

        }else{

            ActivityCompat.requestPermissions(this,arrayOf(ACCESS_FINE_LOCATION), requestCode)

        }


    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){

            100 -> {

                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){


                    Toast.makeText(this,"권한이 승인되었습니다.",Toast.LENGTH_SHORT).show()

                }


            }


        }
    }


    companion object {

        private const val requestCode: Int = 101

    }
}