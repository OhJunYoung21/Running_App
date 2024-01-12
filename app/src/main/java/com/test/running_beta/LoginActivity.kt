package com.test.running_beta

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.test.running_beta.ApplicationClass.MyApplication
import com.test.running_beta.Search.SearchActivity
import com.test.running_beta.databinding.ActivityLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val foundId = intent.getStringExtra("foundId") ?: ""
        binding.loginId.setText(foundId)

        val application = application as MyApplication

        binding.join.setOnClickListener {
            val intent = Intent(this, JoinActivity::class.java)
            startActivity(intent)
        }
        //로그인 버튼 클릭시, roomDB를 조회해서 올바른 아이디와 비밀번호를 사용하였는지 확인한다.
        binding.loginBtn.setOnClickListener {

            //입력된 id와 password를 저장한다.
            val id = binding.loginId.text.toString()
            val password = binding.loginPw.text.toString()

            CoroutineScope(Dispatchers.IO).launch {
                //TODO QUERY 수정(selectId 구현)
                if (application.db.getUserDAO().selectId(id) == id) {
                    //login 이 성공 했을 경우,EncryptedSharedPreference 객체에 상태와 id,password 저장
                    if (application.db.getUserDAO().getPasswordByEmail(id) == password) {
                        runOnUiThread {
                            application.successLogin(id)
                        }
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    //password 예외 처리
                    else {
                        runOnUiThread {
                            Toast.makeText(
                                this@LoginActivity,
                                "비밀번호를 다시 입력하세요.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
                //아이디 입력 오류 예외 처리
                else {
                    runOnUiThread {
                        Toast.makeText(this@LoginActivity, "존재하지 않는 아이디입니다.", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }

        }

        //TODO 아이디 찾기 완료 시, 돌아왔을 때 아이디는 자동입력이 되는것이 좋지 않을까요?
        binding.findId.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }

        binding.findPw.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
    }

    private fun logOut() {
        val myApplication = application as MyApplication
        myApplication.saveLoginStatus(false)
        myApplication.saveLoggedInId("")
    }

    override fun onDestroy() {
        super.onDestroy()
        logOut()
    }
}