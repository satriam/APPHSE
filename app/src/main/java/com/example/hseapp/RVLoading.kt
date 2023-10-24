package com.example.hseapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.hseapp.Dumping.TerkirimLoading
import com.example.hseapp.Loading.AccSpvFragment
import com.example.hseapp.adapter.ViewPagerAdapter
import com.example.hseapp.Loading.PendingFragment
import com.example.hseapp.Loading.SuccessFragment
import com.example.hseapp.retrofit.SessionManager
import com.google.android.material.tabs.TabLayout

class RVLoading : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rvloading)


        val adapter = ViewPagerAdapter(supportFragmentManager)
        sessionManager = SessionManager(this)
        if (sessionManager.getRole() == "user") {
            adapter.addFragment(PendingFragment(), "Pending")
            adapter.addFragment(SuccessFragment(), "Terkirim")
            adapter.addFragment(AccSpvFragment(), "ACC SPV")
        }else if (sessionManager.getRole()=="Supervisor"){
            adapter.addFragment(PendingFragment(), "Pending")
            adapter.addFragment(TerkirimLoading(), "Terkirim")
            adapter.addFragment(SuccessFragment(), "Belum Acc")
            adapter.addFragment(AccSpvFragment(), "ACC SPV")
        }else{
            adapter.addFragment(PendingFragment(), "Pending")
            adapter.addFragment(SuccessFragment(), "Terkirim")
            adapter.addFragment(AccSpvFragment(), "ACC SPV")
        }
        val viewPager = findViewById<ViewPager>(R.id.viewPager) // Mengakses ViewPager dengan findViewById
        val tabLayout = findViewById<TabLayout>(R.id.tbLayout) // Mengakses TabLayout dengan findViewById

        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }

}
