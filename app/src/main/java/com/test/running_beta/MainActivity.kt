package com.test.running_beta

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.test.running_beta.ApplicationClass.MyApplication
import com.test.running_beta.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        //로그 아웃 버튼 클릭 시 이동
        binding.run.setOnClickListener {
            val intent = Intent(this, RunActivity::class.java)
            startActivity(intent)
        }

        binding.logoutBtn.setOnClickListener {
            logOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


    fun logOut() {
        val myApplication = application as MyApplication
        myApplication.saveLoginStatus(false)
        myApplication.saveLoggedInId("")
    }
}