package com.test.running_beta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        //아이디 중복확인 버튼 클릭시 발생하는 이벤트 처리
        binding.checkId.setOnClickListener {

            //아이디가 중복되는 경우

            val newThread = Runnable {

                id = binding.userId.text.toString()
                if (db.getUserDAO().getIdList().contains(id)) {

                }

                //아이디가 중복되지 않는 경우, 메세지 출력(토스트 메세지 또는 다이알로그)
                else{

                }
            }
            val thread = Thread(newThread)

            thread.start()

        }


    }
}