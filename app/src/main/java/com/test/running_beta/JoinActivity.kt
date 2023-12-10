package com.test.running_beta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.UiThread
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.common.internal.Objects.ToStringHelper
import com.test.running_beta.databinding.ActivityJoinBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class JoinActivity : AppCompatActivity() {

    lateinit var binding: ActivityJoinBinding

    lateinit var name: String
    lateinit var number: String
    lateinit var id: String
    lateinit var password: String
    lateinit var passwordRe: String
    lateinit var gender: String

    lateinit var list: ArrayList<String>

    lateinit var User: UserEntity

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

        list = ArrayList<String>()

        //아이디 유효성 검사

        binding.checkId.setOnClickListener {

            id = binding.userId.text.toString()

            lifecycleScope.launch {

                val idExist = checkId(id)

                if (idExist) {
                    withContext(Dispatchers.Main) {
                        binding.userId.text = null
                        Toast.makeText(this@JoinActivity, "아이디가 이미 존재합니다.", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {

                    Toast.makeText(this@JoinActivity, "사용가능한 아이디 입니다.", Toast.LENGTH_SHORT).show()

                    list.add(id)

                }
            }
        }


        //회원가입 기능 구현

        binding.joinBtn.setOnClickListener {

            name = binding.userName.text.toString()

            number = binding.userNumber.text.toString()

            id = binding.userId.text.toString()

            password = binding.userPw.text.toString()

            passwordRe = binding.userPwRe.text.toString()

            gender = ""

            list.add(name)
            list.add(number)

            //입력한 password 가 서로 다른 경우, 여기 까지 정상 작동 이라면 list 에는 4가지 요소가 있을 것.

            if (checkPw(password, passwordRe)) {
                list.add(password)
            }

            if (binding.genderMale.isChecked) {

                gender = "male"
                list.add(gender)

            } else if (binding.genderFemale.isChecked) {

                gender = "female"
                list.add(gender)

            }

            //모든 요소들이 정상입력되었는지 확인하고, 입력되었다면 if문안의 명령문 실행, 아니라면 else문 안의 명령문 실행

            if (list.size == 5) {

                lifecycleScope.launch {

                    withContext(Dispatchers.IO) {

                        User = UserEntity(
                            name = name,
                            phoneNumber = number,
                            id = id,
                            password = password,
                            gender = gender
                        )

                        db.getUserDAO().insertUser(User)

                        runOnUiThread {
                            Toast.makeText(
                                this@JoinActivity, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT
                            ).show()
                        }

                    }
                }

                val intent = Intent(this, LoginActivity::class.java)

                startActivity(intent)

            } else {

                Toast.makeText(this, "입력하지 않은 것들이 있습니다.\n다시 입력하세요", Toast.LENGTH_SHORT).show()

            }
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


    suspend fun checkId(id: String): Boolean {

        return withContext(Dispatchers.IO) {

            db.getUserDAO().getIdList().contains(id)

        }

    }

    fun checkPw(pw1: String, pw2: String): Boolean {

        return (pw1 == pw2)

    }


}