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
    lateinit var name:String
    lateinit var number:String
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

        //runOnUiThread{ Toast.makeText(this, "비밀번호가 서로 일치 하지 않습니다..", Toast.LENGTH_SHORT).show() }

        //late init 변수에 값 할당
        name = binding.userName.toString()

        number = binding.userNumber.toString()

        id = binding.userId.toString()

        password = binding.userPw.toString()

        binding.checkId.setOnClickListener {

            val checkThread = Runnable {

                //아이디 유효성 검사 완료

                if(db.getUserDAO().getIdList().contains(id)){

                    runOnUiThread { Toast.makeText(this,"다른 사용자가 사용중인 아이디입니다.",Toast.LENGTH_SHORT).show() }

                }


            }
            val thread = Thread(checkThread)

            thread.start()


        }



        //아이디 중복확인 버튼 클릭시 발생하는 이벤트 처리
        binding.joinBtn.setOnClickListener {

            //db에 접근하는 작업은 새로운 Thread에서 실행해줘야 Main Thread에 과부하가 걸리지 않는다.

            val newThread = Runnable {

                db.getUserDAO().insertUser(UserEntity(name = name, phoneNumber = number,id = id,password=password,gender = " "))

                runOnUiThread { Toast.makeText(this,"회원가입이 완료되었습니다.",Toast.LENGTH_SHORT).show() }

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