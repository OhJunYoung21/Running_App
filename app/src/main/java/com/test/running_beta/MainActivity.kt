package com.test.running_beta

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(isLoggedIn()){

            val intent = Intent(this,JoinActivity::class.java)
            startActivity(intent)

        }else{

            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)

        }
    }
    fun isLoggedIn(): Boolean {
        val preference = this.getSharedPreferences("login_pref", Context.MODE_PRIVATE)
        //default 값 으로는 false 를 리턴 한다.
        return preference.getBoolean("isLoggedIn", false)
    }
}