package com.example.hseapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.hseapp.Hauling.PendingHauling
import com.example.hseapp.Hauling.SuccessHauling
import com.example.hseapp.Loading.PendingFragment
import com.example.hseapp.Loading.SuccessFragment
import com.example.hseapp.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout

class HistoryHauling : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_hauling)
        val adapter = ViewPagerAdapter(supportFragmentManager)

        adapter.addFragment(PendingHauling(), "Pending")
        adapter.addFragment(SuccessHauling(), "Terkirim")

        val viewPager = findViewById<ViewPager>(R.id.viewPager) // Mengakses ViewPager dengan findViewById
        val tabLayout = findViewById<TabLayout>(R.id.tbLayout) // Mengakses TabLayout dengan findViewById

        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }
}