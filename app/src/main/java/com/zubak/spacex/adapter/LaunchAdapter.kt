package com.zubak.spacex.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zubak.spacex.R
import com.zubak.spacex.core.inflate
import com.zubak.spacex.data.Launches
import com.zubak.spacex.holder.LaunchHolder

class LaunchAdapter(var launches: Launches) : RecyclerView.Adapter<LaunchHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchHolder {
        val inflatedView = parent.inflate(R.layout.launch_holder, false)
        return LaunchHolder(inflatedView)
    }

    override fun getItemCount(): Int = launches.size

    override fun onBindViewHolder(holder: LaunchHolder, position: Int) {
        holder.initialize(launches[position])
    }
}
