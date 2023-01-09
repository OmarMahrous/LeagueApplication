package com.digitalcreativity.leagueapplication.ui.teams

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.NavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.digitalcreativity.leagueapplication.R
import com.digitalcreativity.leagueapplication.data.model.Team
import com.digitalcreativity.leagueapplication.databinding.TeamListItemBinding
import com.digitalcreativity.leagueapplication.ui.util.ImageUrlUtil
import com.digitalcreativity.leagueapplication.ui.util.ScreensNavigator
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.squareup.picasso.Picasso

class TeamsAdapter(
    val context: Context,
    val navController: NavController
): ListAdapter<Team, TeamsAdapter.TeamsViewHolder>(DiffCallback()) {

    private val TAG = "TeamsAdapter"

    inner class TeamsViewHolder(val binding: TeamListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(team: Team) {
            binding.apply {
                this.team = team

                try {

                    val imageUrl = team.crestUrl

                    if (imageUrl?.endsWith("svg") == true){
                        ImageUrlUtil.loadSvgImageFromUrl(context, imageUrl, teamImageView)
                    }else{
                        Picasso.get()
                        .load(team.crestUrl)
                        .placeholder(R.mipmap.ic_placeholder)
                        .into(teamImageView)


                    }



                }catch (e:Exception){
                    Log.e(TAG, "bind: error load image : ${e.message}" )
                }

                executePendingBindings()
            }
        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamsViewHolder {
        val binding =
            TeamListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return TeamsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TeamsViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)

        holder.itemView.setOnClickListener {
            currentItem?.let {
                    item->item.id?.let { id ->
                    ScreensNavigator.navigateToTeamDetails(navController, id)
                }
            }

        }

    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    class DiffCallback : DiffUtil.ItemCallback<Team>() {
        override fun areItemsTheSame(oldItem: Team, newItem: Team): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Team, newItem: Team): Boolean {
            return oldItem == newItem
        }
    }



}