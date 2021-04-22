package com.zubak.spacex.ui.launches

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.hypertrack.hyperlog.HLCallback
import com.hypertrack.hyperlog.HyperLog
import com.hypertrack.hyperlog.error.HLErrorResponse
import com.zubak.spacex.R
import com.zubak.spacex.adapter.LaunchAdapter
import com.zubak.spacex.api.LaunchesType
import com.zubak.spacex.core.DataManager
import com.zubak.spacex.data.Launch
import com.zubak.spacex.data.Launches
import com.zubak.spacex.ui.filters.Filters


class LaunchesFragment : Fragment(),
    LifecycleOwner {

    private lateinit var launchesViewModel: LaunchesViewModel
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyPlaceholder: LinearLayout
    private lateinit var adapter: LaunchAdapter
    private lateinit var launchesType: LaunchesType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launchesType =
            LaunchesType.valueOf(
                arguments?.getString(getString(R.string.launches_type_bundle))
                    ?: LaunchesType.ALL.name
            )
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vmFactory = LaunchesViewModelFactory(context, launchesType)
        launchesViewModel = ViewModelProviders
            .of(this, vmFactory)
            .get(LaunchesViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_launches, container, false)

        emptyPlaceholder = root.findViewById(R.id.empty_placeholder)
        recyclerView = root.findViewById(R.id.allLaunches) as RecyclerView

        swipeRefreshLayout = root.findViewById(R.id.swipe_refresh_container)
        swipeRefreshLayout.isRefreshing = true
        swipeRefreshLayout.setOnRefreshListener {
            launchesViewModel.refreshLaunches()
        }

        adapter = LaunchAdapter(launchesViewModel.launches.value ?: Launches(), context)

        launchesViewModel.launches.observe(viewLifecycleOwner, Observer {
            adapter.launches = launchesViewModel.launches.value ?: Launches()

            with(adapter.launches.isEmpty()) {
                emptyPlaceholder.visibility = if (this) View.VISIBLE else View.GONE
                recyclerView.visibility = if (this) View.GONE else View.VISIBLE
            }

            sortLaunches(adapter.launches)
            adapter.notifyDataSetChanged()
            swipeRefreshLayout.isRefreshing = false
        })

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter


        requireActivity().supportFragmentManager.addOnBackStackChangedListener {
            sortLaunches(adapter.launches)
            adapter.notifyDataSetChanged()
        }


        HyperLog.pushLogs(context, false, object: HLCallback() {

            override fun onSuccess(response: Any?) {
                Log.e("TAG", "sending successful")
            }

            override fun onError(HLErrorResponse: HLErrorResponse?) {
                Log.e("TAG", "sending FAILED!!!!")
                Log.e("TAG", HLErrorResponse?.errorCode.toString())
            }
        })
        HyperLog.e("LaunchesFragment", "tried to push logs...")
        return root
    }

    private fun sortLaunches(launches: Launches) {
        launches.sortBy { launch: Launch ->
            context?.let {
                when (DataManager().getFilter(it)) {
                    Filters.MISSION_NAME -> launch.missionName
                    Filters.ROCKET_NAME -> launch.rocket?.rocketName
                }
            }
        }
    }
}

