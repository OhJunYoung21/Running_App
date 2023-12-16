package com.test.running_beta.Search

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.test.running_beta.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {

    private val tabTitleArray = arrayOf("아이디 찾기", "비밀번호 찾기")

    private lateinit var binding: ActivitySearchBinding

    private lateinit var TabLayout: TabLayout

    // private lateinit var ViewPager2Adapter:ViewPager2Adapter

    private lateinit var ViewPager2: ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchBinding.inflate(layoutInflater)

        val view = binding.root

        setContentView(view)

        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.setTitle("아이디/비밀번호 찾기")

        TabLayout = binding.TabLayout

        ViewPager2 = binding.pager

        ViewPager2.adapter = ViewPager2Adapter(supportFragmentManager, lifecycle)

        TabLayoutMediator(TabLayout, ViewPager2) { tab, position ->

            tab.text = tabTitleArray[position]

        }.attach()


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
}