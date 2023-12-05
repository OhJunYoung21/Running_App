package com.test.running_beta

import android.content.Intent
import android.graphics.Paint.Join
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.room.Database
import com.test.running_beta.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding:ActivityLoginBinding

    private lateinit var db:AppDatabase

    private lateinit var id:String
    private lateinit var password:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.join.setOnClickListener {

            val intent:Intent = Intent(this,JoinActivity::class.java)

            startActivity(intent)

        }
        //로그인 버튼 클릭시, roomDB를 조회해서 올바른 아이디와 비밀번호를 사용하였는지 확인한다.
        binding.loginBtn.setOnClickListener {

            //입력된 id와 password를 저장한다.
            id = binding.loginId.text.toString()
            password = binding.loginPw.text.toString()

            //비동기화 처리를 위해 새로운 스레드를 사용한다.
            val loginThread = Runnable {

                db = AppDatabase.getInstance(this)

                if(db.getUserDAO().getIdList().contains(id)){
                    //로그인이 성공했을 경우
                    if(db.getUserDAO().getPasswordByEmail(id) == password){
                        runOnUiThread{Toast.makeText(this,"로그인 되었습니다.",Toast.LENGTH_SHORT).show()}
                    }
                    //비밀번호 예외 처리
                    else {
                        runOnUiThread{Toast.makeText(this,"비밀번호를 다시 입력하세요.",Toast.LENGTH_SHORT).show()}
                    }

                }
                //아이디 입력 오류 예외처리
                else{
                    runOnUiThread{Toast.makeText(this,"존재하지 않는 아이디입니다.",Toast.LENGTH_SHORT).show()}
                }

            }

            val thread = Thread(loginThread)

            thread.start()


        }





    }
}