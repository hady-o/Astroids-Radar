package com.udacity.asteroidradar.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.databinding.AsteroidItemLayoutBinding

class AsAdapter(val clickListener: AsteroidListenerClass): ListAdapter<Asteroid, AsAdapter.ViewHolder>(AsteroidDiffCallBack()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.fromViewHolder(parent)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            var asteroid = getItem(position)!!
            holder.astroidBind(asteroid)
    }




    public class  ViewHolder private constructor(val binding: AsteroidItemLayoutBinding):RecyclerView.ViewHolder(binding.root)
    {
        companion object {
             fun fromViewHolder(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AsteroidItemLayoutBinding.inflate(layoutInflater,parent,false)
                return ViewHolder(binding)
            }
        }

       fun astroidBind(
            asteroid: Asteroid?
        ) {
           binding.asteroid = asteroid
           binding.executePendingBindings()
        }
    }

    class AsteroidDiffCallBack : DiffUtil.ItemCallback<Asteroid>()
    {
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem == newItem
        }

    }
    class AsteroidListenerClass(val clickListener:(asteroid:Asteroid)->Unit){
        fun onClick(asteroid: Asteroid) = clickListener(asteroid)
    }


}