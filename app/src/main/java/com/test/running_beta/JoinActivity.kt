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
            id = binding.userId.text.toString()
            if (db.getUserDAO().getIdList().contains(id)) {
                Toast.makeText(
                    this, "이미 사용중인 아이디입니다.\n 다시 입력하세요", Toast.LENGTH_SHORT
                )
            }
        }


    }
}