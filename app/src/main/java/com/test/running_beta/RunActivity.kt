package com.test.running_beta

import android.Manifest
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.ACTIVITY_RECOGNITION
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.test.running_beta.UI.RunBottomSheetFragment
import com.test.running_beta.databinding.ActivityRunBinding

class RunActivity : AppCompatActivity() {
    lateinit var binding: ActivityRunBinding

    private val permission: Array<String> = arrayOf(
        ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION,
        ACCESS_COARSE_LOCATION
    )

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Toast.makeText(this, "권한이 승인 되었습니다.", Toast.LENGTH_SHORT).show()
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.ACTIVITY_RECOGNITION, ACCESS_FINE_LOCATION,
                        ACCESS_COARSE_LOCATION
                    ),
                    requestCode
                )
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRunBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.setIcon(R.drawable.arrow_button)

        supportActionBar?.title = ""

        requestPermission.launch(ACTIVITY_RECOGNITION)


        binding.showRoute.setOnClickListener {
            val sheet = RunBottomSheetFragment(this)
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

    companion object {
        internal const val requestCode: Int = 101
    }
}
