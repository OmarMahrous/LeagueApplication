package com.digitalcreativity.leagueapplication.ui.teams.team_details

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.digitalcreativity.leagueapplication.data.model.Squad
import com.digitalcreativity.leagueapplication.databinding.TeamPlayerListItemBinding

class SquadPlayersAdapter(
): ListAdapter<Squad, SquadPlayersAdapter.SquadViewHolder>(DiffCallback()) {

    private val TAG = "TeamsAdapter"

    inner class SquadViewHolder(val binding: TeamPlayerListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(squadPlayer: Squad, position: Int) {
            binding.apply {

                this.teamPlayer = squadPlayer

                counterTextView.text = "${position+1}"

                executePendingBindings()
            }
        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SquadViewHolder {
        val binding =
            TeamPlayerListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return SquadViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SquadViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem, position)


    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    class DiffCallback : DiffUtil.ItemCallback<Squad>() {
        override fun areItemsTheSame(oldItem: Squad, newItem: Squad): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Squad, newItem: Squad): Boolean {
            return oldItem == newItem
        }
    }



}