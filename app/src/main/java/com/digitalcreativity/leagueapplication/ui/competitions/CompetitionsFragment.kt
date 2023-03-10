package com.digitalcreativity.leagueapplication.ui.competitions

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.digitalcreativity.leagueapplication.R
import com.digitalcreativity.leagueapplication.data.model.Competition
import com.digitalcreativity.leagueapplication.data.source.local.LeagueDatabase
import com.digitalcreativity.leagueapplication.data.source.local.competitions.CompetitionsDao
import com.digitalcreativity.leagueapplication.data.source.remote.ApiGenerator
import com.digitalcreativity.leagueapplication.data.source.remote.competitions.CompetitionsApi
import com.digitalcreativity.leagueapplication.data.util.Status
import com.digitalcreativity.leagueapplication.databinding.FragmentCompetitionsBinding
import com.digitalcreativity.leagueapplication.di.appModule
import com.digitalcreativity.leagueapplication.ui.BaseFragment
import com.digitalcreativity.leagueapplication.ui.MainActivity
import com.digitalcreativity.leagueapplication.util.NetworkHelper
import org.koin.android.ext.android.inject
import retrofit2.Retrofit


class CompetitionsFragment : BaseFragment(R.layout.fragment_competitions) {

    private val TAG = "CompetitionsFragment"

    private lateinit var viewModel: CompetitionsViewModel

    private val networkHelper:NetworkHelper by inject()
    private val competitionsApi:CompetitionsApi by inject()
    private val competitionsDao:CompetitionsDao by inject()

    private var _binding: FragmentCompetitionsBinding? =null
    private val binding: FragmentCompetitionsBinding get() =_binding!!

    private var listAdapter:CompetitionsAdapter?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentCompetitionsBinding.inflate(inflater, container, false)

        (requireActivity() as MainActivity).title = getString(R.string.competitions)

        initRecyclerView()

        initViewModel()

        return binding.root
    }


    private fun initRecyclerView() {
        listAdapter = context?.let { CompetitionsAdapter(it,findNavController()) }
        binding.competitionsRecyclerView.adapter = listAdapter
    }

    private fun initViewModel() {

        val viewModelFactory = CompetitionsViewModel
            .CompetitionsViewModelFactory(competitionsApi, competitionsDao)

        viewModel = ViewModelProvider(this, viewModelFactory)[CompetitionsViewModel::class.java]

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchCompetitions()


        checkInternetConnection()

    }

    private fun checkInternetConnection() {
        if (networkHelper.isNetworkConnected())
            getCompetitions()
        else
            getCompetitionsFromLocal()
    }

    private fun fetchCompetitions(){
        viewModel.fetchData()
    }

    private fun getCompetitions() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.getCompetitions().collect { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {


                        updateUiListComponent(resource.data)


                        Log.d(TAG, "getCompetitions: list size = ${resource.data?.size}")
                    }
                    Status.ERROR ->
                        context?.let { showErrorMessage(it, resource.message?:"Unknown error") }

                    else -> { // TODO show loading progress }
                    }
                }
            }

        }
    }

    private fun getCompetitionsFromLocal(){
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.getCompetitionsFromLocal().collect { list ->


                Log.d(TAG, "getCompetitionsFromLocal: list = ${list.size}")
                        updateUiListComponent(list)

                }
            }

    }

    private fun updateUiListComponent(competitionList: List<Competition?>?) {
        binding.competitionsRecyclerView.apply {
            if (adapter==null)
                adapter = listAdapter

            listAdapter?.submitList(competitionList)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
