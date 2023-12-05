package com.test.running_beta

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.test.running_beta.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)

        //로그인 된 상태가 아니라면 로그인 화면으로 이동
        if (!isLoggedIn()) {

            val intent = Intent(this, LoginActivity::class.java)

            startActivity(intent)

        }

        binding.logout.setOnClickListener {

            Toast.makeText(this,"로그아웃 되었습니다.",Toast.LENGTH_SHORT).show()

        }
    }

    fun isLoggedIn(): Boolean {
        val preference = this.getSharedPreferences("login_pref", Context.MODE_PRIVATE)
        //default 값 으로는 false 를 리턴 한다.
        return preference.getBoolean("isLoggedIn", false)
    }

    fun logOut() {

        saveLoginStatus(false)
        saveLoggedInId("")

    }

    fun saveLoginStatus(loggedIn: Boolean) {
        val preference = this.getSharedPreferences("login_pref", Context.MODE_PRIVATE)
        val editor = preference.edit()
        editor.putBoolean("isLoggedIn", loggedIn)
        editor.apply()
    }

    fun saveLoggedInId(userId: String) {
        val sharedPreferences = this.getSharedPreferences("login_pref", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("loggedInUserId", userId)
        editor.apply()
    }
}