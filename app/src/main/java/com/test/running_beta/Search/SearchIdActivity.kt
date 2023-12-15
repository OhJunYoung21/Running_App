package com.test.running_beta.Search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.test.running_beta.ApplicationClass.MyApplication
import com.test.running_beta.databinding.ActivitySearchIdBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchIdActivity : AppCompatActivity() {

    lateinit var binding:ActivitySearchIdBinding

    lateinit var name:String
    lateinit var number:String

    lateinit var id:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchIdBinding.inflate(layoutInflater)

        val view = binding.root

        setContentView(view)

        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.setTitle(" ")


        binding.searchBtn.setOnClickListener {

            name = binding.searchName.toString().trim()

            number = binding.searchNumber.toString().trim()

            CoroutineScope(Dispatchers.IO).launch {

                id = findId(name,number)

                runOnUiThread {

                    Toast.makeText(this@SearchIdActivity,"회원님의 아이디는 ${id}입니다.",Toast.LENGTH_SHORT).show()

                }
4
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

    private suspend fun findId(name: String, number: String): String {

        val application = application as MyApplication

        return application.db.getUserDAO().getIdByName(name, number)

    }
}