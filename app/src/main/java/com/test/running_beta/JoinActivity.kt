package com.test.running_beta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.room.Database
import com.test.running_beta.databinding.ActivityJoinBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class JoinActivity : AppCompatActivity() {

    lateinit var binding: ActivityJoinBinding
    lateinit var name: String
    lateinit var number: String
    lateinit var id: String
    lateinit var password: String

    lateinit var gender: String

    lateinit var db: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityJoinBinding.inflate(layoutInflater)

        setContentView(binding.root)


        db = AppDatabase.getInstance(this)

        //toolbar에 뒤로가기 버튼 구현

        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.setTitle(" ")

        gender = ""

        binding.joinBtn.setOnClickListener {


            name = binding.userName.text.toString()

            number = binding.userNumber.text.toString()

            id = binding.userId.text.toString()

            password = binding.userPw.text.toString()

            val joinThread = Runnable {

                db.getUserDAO().insertUser(
                    UserEntity(
                        name = name,
                        phoneNumber = number,
                        id = id,
                        password = password,
                        gender = "",
                        record = Records(0L, 0L, 0L,0L)
                    )
                )

            }

            runOnUiThread { Toast.makeText(this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show() }

            val thread = Thread(joinThread)

            thread.start()

            finish()

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
}