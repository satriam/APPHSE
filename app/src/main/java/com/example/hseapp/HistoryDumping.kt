package com.example.hseapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.hseapp.Dumping.PendingDumping
import com.example.hseapp.Dumping.SuccessDumping
import com.example.hseapp.Dumping.TerkirimDumping
import com.example.hseapp.Hauling.PendingHauling
import com.example.hseapp.Hauling.SuccessHauling
import com.example.hseapp.Loading.DumpingSpvFragment
import com.example.hseapp.adapter.ViewPagerAdapter
import com.example.hseapp.retrofit.SessionManager
import com.google.android.material.tabs.TabLayout

class HistoryDumping : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_dumping)
        val adapter = ViewPagerAdapter(supportFragmentManager)

        sessionManager = SessionManager(this)
        if (sessionManager.getRole() == "user") {
            adapter.addFragment(PendingDumping(), "Pending")
            adapter.addFragment(SuccessDumping(), "Terkirim")
            adapter.addFragment(DumpingSpvFragment(), "ACC Laporan")
        }else if (sessionManager.getRole()=="Supervisor"){
            adapter.addFragment(PendingDumping(), "Pending")
            adapter.addFragment(TerkirimDumping(), "Terkirim")
            adapter.addFragment(SuccessDumping(), "Belum Acc")
            adapter.addFragment(DumpingSpvFragment(), "ACC SPV")
        }else{
            adapter.addFragment(PendingDumping(), "Pending")
            adapter.addFragment(SuccessDumping(), "Terkirim")
            adapter.addFragment(DumpingSpvFragment(), "ACC Laporan")
        }

        val viewPager = findViewById<ViewPager>(R.id.viewPagerdumping) // Mengakses ViewPager dengan findViewById
        val tabLayout = findViewById<TabLayout>(R.id.tbLayoutdumping) // Mengakses TabLayout dengan findViewById

        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }
}