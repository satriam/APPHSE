package com.example.hseapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.hseapp.Dumping.PendingDumping
import com.example.hseapp.Dumping.SuccessDumping
import com.example.hseapp.Dumping.TerkirimHauling
import com.example.hseapp.Hauling.PendingHauling
import com.example.hseapp.Hauling.SuccessHauling
import com.example.hseapp.Loading.DumpingSpvFragment
import com.example.hseapp.Loading.HaulingSpvFragment
import com.example.hseapp.adapter.ViewPagerAdapter
import com.example.hseapp.retrofit.SessionManager
import com.google.android.material.tabs.TabLayout

class HistoryHauling : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_hauling)
        val adapter = ViewPagerAdapter(supportFragmentManager)
        sessionManager = SessionManager(this)
        if (sessionManager.getRole() == "user") {
        adapter.addFragment(PendingHauling(), "Pending")
        adapter.addFragment(SuccessHauling(), "Terkirim")
        adapter.addFragment(HaulingSpvFragment(), "ACC Laporan")
        }else if (sessionManager.getRole()=="Supervisor"){
            adapter.addFragment(PendingHauling(), "Pending")
            adapter.addFragment(TerkirimHauling(), "Terkirim")
            adapter.addFragment(SuccessHauling(), "Belum Acc")
            adapter.addFragment(HaulingSpvFragment(), "ACC SPV")

        }else{
            adapter.addFragment(PendingHauling(), "Pending")
            adapter.addFragment(SuccessHauling(), "Terkirim")
            adapter.addFragment(HaulingSpvFragment(), "ACC Laporan")
        }
        val viewPager = findViewById<ViewPager>(R.id.viewPager) // Mengakses ViewPager dengan findViewById
        val tabLayout = findViewById<TabLayout>(R.id.tbLayout) // Mengakses TabLayout dengan findViewById

        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }
}