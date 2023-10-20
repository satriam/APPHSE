package com.example.hseapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.hseapp.Dumping.PendingDumping
import com.example.hseapp.Dumping.SuccessDumping
import com.example.hseapp.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout

class HistoryDumping : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_dumping)
        val adapter = ViewPagerAdapter(supportFragmentManager)

        adapter.addFragment(PendingDumping(), "Pending")
        adapter.addFragment(SuccessDumping(), "Terkirim")
        adapter.addFragment(SuccessDumping(), "ACC Laporan")

        val viewPager = findViewById<ViewPager>(R.id.viewPagerdumping) // Mengakses ViewPager dengan findViewById
        val tabLayout = findViewById<TabLayout>(R.id.tbLayoutdumping) // Mengakses TabLayout dengan findViewById

        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }
}