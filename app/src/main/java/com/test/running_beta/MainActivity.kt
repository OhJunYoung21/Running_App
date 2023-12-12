package com.test.running_beta

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.test.running_beta.ApplicationClass.MyApplication
import com.test.running_beta.databinding.ActivityMainBinding
import kotlin.math.log


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        //로그인 된 상태가 아니라면 로그인 화면으로 이동
        if (!isLoggedIn()) {

            val intent = Intent(this, LoginActivity::class.java)

            startActivity(intent)

        }
        //로그 아웃 버튼 클릭 시 이동
        binding.logout.setOnClickListener {

            //logOut()

            val intent = Intent(this, LoginActivity::class.java)

            startActivity(intent)

            finish()

            Toast.makeText(this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show()

        }


        binding.Running.setOnClickListener {

            val intent = Intent(this, InsideActivity::class.java)
            startActivity(intent)

        }
    }

    fun isLoggedIn(): Boolean {

        val myApplication = application as MyApplication

        val sharedPreference = myApplication.sharedPreferences

        return sharedPreference.getBoolean("isLoggedIn",false)
    }

    fun logOut() {

        val myApplication = application as MyApplication

        myApplication.saveLoginStatus(false)
        myApplication.saveLoggedInId("")

    }



}