package com.example.hseapp.Loading

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.hseapp.R
import com.example.hseapp.adapter.AdapterLoading
import com.example.hseapp.dataclass.Loading
import com.example.hseapp.retrofit.RetrofitInstance
import com.example.hseapp.retrofit.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HaulingSpvFragment : Fragment() {
    private lateinit var sessionManager: SessionManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AdapterLoading
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_success_hauling, container, false)

        recyclerView = rootView.findViewById(R.id.rv_datahauling)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        swipeRefreshLayout = rootView.findViewById(R.id.swipehauling)
        swipeRefreshLayout.setOnRefreshListener {
            // Implement refresh action here
            loadData()
            swipeRefreshLayout.isRefreshing = false
        }

        loadData()

        return rootView
    }

    private fun loadData() {
        val apiClient = RetrofitInstance.Create(requireContext())
        sessionManager = SessionManager(requireContext())

        val apiService = apiClient.getrecentaccHauling()
        val apiadmin = apiClient.getHaulingacc()
        val apispv = apiClient.getrecentaccspvHauling()
        val listData = ArrayList<Loading>()

        if (sessionManager.getRole() == "admin") {
            apiadmin.enqueue(object : Callback<ArrayList<Loading>> {
                override fun onResponse(
                    call: Call<ArrayList<Loading>>,
                    response: Response<ArrayList<Loading>>
                ) {
                    if (response.isSuccessful) {
                        val dataMe = response.body()
                        Log.e("DATA LOADING", dataMe.toString())

                        if (dataMe != null) {
                            listData.addAll(dataMe)
                            adapter = AdapterLoading(listData,sessionManager)
                            recyclerView.adapter = adapter
                        } else {
                            // Handle case when dataMe is null
                        }
                    } else {
                        val errorMessage = response.message()
                        // Handle error message
                    }
                }

                override fun onFailure(call: Call<ArrayList<Loading>>, t: Throwable) {
                    Log.e("DATA LOADING", t.toString())
                    // Handle failure
                }
            })
        } else if(sessionManager.getRole() == "Supervisor"){
            apispv.enqueue(object : Callback<ArrayList<Loading>> {
                override fun onResponse(
                    call: Call<ArrayList<Loading>>,
                    response: Response<ArrayList<Loading>>
                ) {
                    if (response.isSuccessful) {
                        val dataMe = response.body()
                        Log.e("DATA LOADING", dataMe.toString())

                        if (dataMe != null) {
                            listData.addAll(dataMe)
                            adapter = AdapterLoading(listData,sessionManager)
                            recyclerView.adapter = adapter
                        } else {
                            // Handle case when dataMe is null
                        }
                    } else {
                        val errorMessage = response.message()
                        // Handle error message
                    }
                }

                override fun onFailure(call: Call<ArrayList<Loading>>, t: Throwable) {
                    Log.e("DATA LOADING", t.toString())
                    // Handle failure
                }
            })
        }
        else {

            apiService.enqueue(object : Callback<ArrayList<Loading>> {
                override fun onResponse(
                    call: Call<ArrayList<Loading>>,
                    response: Response<ArrayList<Loading>>
                ) {
                    if (response.isSuccessful) {
                        val dataMe = response.body()
                        Log.e("DATA LOADING", dataMe.toString())

                        if (dataMe != null) {
                            listData.addAll(dataMe)
                            adapter = AdapterLoading(listData,sessionManager)
                            recyclerView.adapter = adapter
                        } else {
                            // Handle case when dataMe is null
                        }
                    } else {
                        val errorMessage = response.message()
                        // Handle error message
                    }
                }

                override fun onFailure(call: Call<ArrayList<Loading>>, t: Throwable) {
                    Log.e("DATA LOADING", t.toString())
                    // Handle failure
                }
            })
        }
    }
}
