package com.test.running_beta

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.test.running_beta.ApplicationClass.MyApplication

class introActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        Handler(Looper.getMainLooper()).postDelayed({
            //로그인 된 상태가 아니라면 로그인 화면으로 이동
            val intent = if (!isLoggedIn()) {
                Intent(this, LoginActivity::class.java)
            } else {
                Intent(this, MainActivity::class.java)
            }
            startActivity(intent)
            finish()
        }, 1000)
    }

    private fun isLoggedIn(): Boolean {
        val myApplication = application as MyApplication
        val sharedPreference = myApplication.sharedPreferences
        return sharedPreference.getBoolean("isLoggedIn", false)
    }
}