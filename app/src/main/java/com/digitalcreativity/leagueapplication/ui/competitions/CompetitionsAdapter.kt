package com.digitalcreativity.leagueapplication.ui.competitions

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.NavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.digitalcreativity.leagueapplication.R
import com.digitalcreativity.leagueapplication.data.model.Competition
import com.digitalcreativity.leagueapplication.databinding.CompetitionListItemBinding
import com.digitalcreativity.leagueapplication.ui.util.ImageUrlUtil
import com.digitalcreativity.leagueapplication.ui.util.ScreensNavigator
import com.squareup.picasso.Picasso

class CompetitionsAdapter(
    val context: Context,
    val navController: NavController
): ListAdapter<Competition, CompetitionsAdapter.CompetitionsViewHolder>(DiffCallback()) {

    private  val TAG = "CompetitionsAdapter"


    inner class CompetitionsViewHolder(val binding: CompetitionListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(competition: Competition) {
            binding.apply {
                this.competition = competition

                try {

                    val imageUrl = competition.emblemUrl

                    if (imageUrl?.endsWith("svg") == true){
                        ImageUrlUtil.loadSvgImageFromUrl(context, imageUrl, competitionImageView)
                    }else{
                    Picasso.get()

                        .load(imageUrl)
                        .placeholder(R.mipmap.ic_placeholder)
                        .into(competitionImageView)
                }
                }catch (e:Exception){
                    Log.e(TAG, "bind: error load image : ${e.message}" )
                }

                executePendingBindings()
            }
        }


    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompetitionsViewHolder {
        val binding =
            CompetitionListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CompetitionsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CompetitionsViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)

        holder.itemView.setOnClickListener {
            currentItem?.let {
                    item->item.id?.let { id ->
                        ScreensNavigator.navigateToCompetitionDetails(navController, id)
                    }
            }

        }

    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    class DiffCallback : DiffUtil.ItemCallback<Competition>() {
        override fun areItemsTheSame(oldItem: Competition, newItem: Competition): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Competition, newItem: Competition): Boolean {
            return oldItem == newItem
        }
    }



}
