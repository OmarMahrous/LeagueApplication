package com.digitalcreativity.leagueapplication.ui.competitions.competition_details

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.digitalcreativity.leagueapplication.R
import com.digitalcreativity.leagueapplication.data.model.CurrentSeason
import com.digitalcreativity.leagueapplication.databinding.SeasonListItemBinding
import com.squareup.picasso.Picasso

class SeasonsAdapter():
    ListAdapter<CurrentSeason, SeasonsAdapter.SeasonsViewHolder>(DiffCallback()) {

    private  val TAG = "SeasonsAdapter"


    inner class SeasonsViewHolder(val binding: SeasonListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(season: CurrentSeason) {
            binding.apply {
                this.season = season

                val winner = season.winner

                if (winner!=null) {

                    try {

                        Picasso.get()

                            .load(winner.crestUrl)
                            .placeholder(R.mipmap.ic_placeholder)
                            .into(winnerImageView)
                    } catch (e: Exception) {
                        Log.e(TAG, "bind: error load image : ${e.message}")
                    }
                }

                executePendingBindings()
            }
        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonsViewHolder {
        val binding =
            SeasonListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return SeasonsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SeasonsViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)

    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    class DiffCallback : DiffUtil.ItemCallback<CurrentSeason>() {
        override fun areItemsTheSame(oldItem: CurrentSeason, newItem: CurrentSeason): Boolean {
            return oldItem.s_id == newItem.s_id
        }

        override fun areContentsTheSame(oldItem: CurrentSeason, newItem: CurrentSeason): Boolean {
            return oldItem == newItem
        }
    }



}