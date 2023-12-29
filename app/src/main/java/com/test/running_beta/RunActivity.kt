package com.test.running_beta

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.test.running_beta.UI.MapFragment
import com.test.running_beta.UI.PermissionDialog
import com.test.running_beta.UI.RunBottomSheetFragment
import com.test.running_beta.databinding.ActivityRunBinding

class RunActivity : AppCompatActivity() {

    private val requestCode: Int = 101

    lateinit var binding: ActivityRunBinding

    private lateinit var sheet: BottomSheetDialogFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRunBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.setIcon(R.drawable.arrow_button)

        supportActionBar?.setTitle("")

        /**권한이 요청되고 승인되었는지를 확인하는 코드**/


        if (ActivityCompat.checkSelfPermission(
                this,
                ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                this, arrayOf(ACCESS_FINE_LOCATION),
                requestCode
            )
        }

        /**지도를 띄우는 코드**/

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


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {


        when (requestCode) {

            101 -> {

                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(this, "권한이 승인되었습니다.", Toast.LENGTH_SHORT).show()

                }
                /**권한 요청이 거부된 경우*/
                else if(grantResults[0] == PackageManager.PERMISSION_DENIED){

                    val dialogFragment = PermissionDialog(this,1)

                    dialogFragment.isCancelable = false

                    dialogFragment.show(supportFragmentManager, "findIdProcess")

                    if(dialogFragment.getCheck() == 1){

                        grantResults[0] == PackageManager.PERMISSION_GRANTED

                        ActivityCompat.requestPermissions(
                            this, arrayOf(ACCESS_FINE_LOCATION),
                            requestCode)

                    }



                }

            }

            else -> {

                super.onRequestPermissionsResult(requestCode, permissions, grantResults)

            }

        }
    }


}
