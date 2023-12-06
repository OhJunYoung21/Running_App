package com.test.running_beta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.test.running_beta.databinding.ActivityInsideBinding

class InsideActivity : AppCompatActivity() {

    private lateinit var binding:ActivityInsideBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInsideBinding.inflate(layoutInflater)

        setContentView(binding.root)


    }
}