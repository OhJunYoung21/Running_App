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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext

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
                    CoroutineScope(Dispatchers.Main).launch {
                        binding.userId.text = null
                        Toast.makeText(this@JoinActivity, "아이디가 이미 존재합니다.", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {

                    Toast.makeText(this@JoinActivity, "사용가능한 아이디 입니다.", Toast.LENGTH_SHORT).show()


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

            //성별이 잘 입력됬는지를 확인

            if (binding.genderMale.isChecked) {

                gender = "male"

            } else if (binding.genderFemale.isChecked) {

                gender = "female"

            } else {

                gender = ""

            }

            if (binding.userId.text == null) {

                Toast.makeText(this, "아이디는 필수 입력사항입니다.", Toast.LENGTH_SHORT).show()

            } else if (binding.userPw.text == null) {

                Toast.makeText(this, "비밀번호는 필수 입력사항입니다.", Toast.LENGTH_SHORT).show()

            } else if (binding.userPwRe.text == null) {

                Toast.makeText(this, "비밀번호를 재확인하세요", Toast.LENGTH_SHORT).show()

            } else if (password != passwordRe) {

                Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()

            } else if (name == "") {

                Toast.makeText(this, "이름은 필수입력 사항입니다.", Toast.LENGTH_SHORT).show()

            } else if (number == "") {

                Toast.makeText(this, "번호는 필수 입력 사항입니다.", Toast.LENGTH_SHORT).show()

            }else if(gender == ""){

                Toast.makeText(this,"성별은 필수입력 사항입니다.",Toast.LENGTH_SHORT).show()

            }


            else {

                lifecycleScope.launch {

                    CoroutineScope(Dispatchers.IO).launch {

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

            }


            //입력한 password 가 서로 다른 경우, 여기 까지 정상 작동 이라면 list 에는 4가지 요소가 있을 것.

            /*else {

               Toast.makeText(this, "입력하지 않은 것들이 있습니다.\n다시 입력하세요", Toast.LENGTH_SHORT).show()

           }*/
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