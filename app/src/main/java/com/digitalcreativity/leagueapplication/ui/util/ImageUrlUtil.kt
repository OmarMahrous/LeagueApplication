package com.digitalcreativity.leagueapplication.ui.util

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.digitalcreativity.leagueapplication.R
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou

class ImageUrlUtil {

    companion object{
        fun loadSvgImageFromUrl(context: Context, imageUrl: String, teamImageView: ImageView) {
            GlideToVectorYou
                .init()
                .with(context)
                .setPlaceHolder(R.mipmap.ic_placeholder, R.mipmap.ic_placeholder)
                .load(Uri.parse(imageUrl), teamImageView);
        }
    }

}