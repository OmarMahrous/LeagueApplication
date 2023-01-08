package com.digitalcreativity.leagueapplication.ui

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment

open class BaseFragment(rootLayoutId:Int) : Fragment(rootLayoutId) {

    fun showErrorMessage(context: Context, msg:String){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

}