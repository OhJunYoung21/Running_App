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
    lateinit var id: String
    lateinit var password: String
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

        //아이디 중복확인 버튼 클릭시 발생하는 이벤트 처리
        binding.checkId.setOnClickListener {

            //아이디가 중복되는 경우

            val newThread = Runnable {

                id = binding.userId.text.toString()
                if (db.getUserDAO().getIdList().contains(id)) {
                    runOnUiThread{ Toast.makeText(this, "이미 사용중인 아이디입니다.\n 다시입력하세요.", Toast.LENGTH_SHORT).show() }
                }

                //아이디가 중복되지 않는 경우, 메세지 출력(토스트 메세지 또는 다이알로그)
                else{
                    runOnUiThread{ Toast.makeText(this, "사용가능한 아이디입니다.", Toast.LENGTH_SHORT).show() }
                }
            }
            val thread = Thread(newThread)

            thread.start()

        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {

                val intent = Intent(this,LoginActivity::class.java)

                startActivity(intent)

                return true

            }
            else -> {}
        }
        return super.onOptionsItemSelected(item)
    }
}