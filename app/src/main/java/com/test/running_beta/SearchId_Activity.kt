package com.test.running_beta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.test.running_beta.databinding.ActivitySearchIdBinding

class SearchId_Activity : AppCompatActivity() {

    lateinit var binding:ActivitySearchIdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchIdBinding.inflate(layoutInflater)

        val view = binding.root

        setContentView(view)



    }
}