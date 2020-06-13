package com.zubak.spacex.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.zubak.spacex.R
import com.zubak.spacex.adapter.LaunchAdapter
import com.zubak.spacex.api.LaunchesType
import com.zubak.spacex.data.Launches


class AllLaunchesFragment : Fragment(), LifecycleOwner {

    private lateinit var allLaunchesViewModel: AllLaunchesViewModel
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: LaunchAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vmFactory = AllLaunchesViewModelFactory(context, LaunchesType.ALL)
        allLaunchesViewModel = ViewModelProviders
            .of(this, vmFactory)
            .get(AllLaunchesViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_all_launches, container, false)

        swipeRefreshLayout = root.findViewById(R.id.swipe_refresh_container)
        swipeRefreshLayout.isRefreshing = true
        swipeRefreshLayout.setOnRefreshListener {
            allLaunchesViewModel.refreshLaunches()
        }

        adapter = LaunchAdapter(allLaunchesViewModel.launches.value ?: Launches(), context)

        allLaunchesViewModel.launches.observe(viewLifecycleOwner, Observer {
            adapter.launches = allLaunchesViewModel.launches.value ?: Launches()
            adapter.notifyDataSetChanged()
            swipeRefreshLayout.isRefreshing = false
        })

        recyclerView = root.findViewById(R.id.allLaunches) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        return root
    }
}

