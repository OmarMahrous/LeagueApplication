package com.digitalcreativity.leagueapplication.ui.teams

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.digitalcreativity.leagueapplication.R
import com.digitalcreativity.leagueapplication.data.model.Team
import com.digitalcreativity.leagueapplication.data.source.local.teams.TeamsDao
import com.digitalcreativity.leagueapplication.data.source.remote.teams.TeamsApi
import com.digitalcreativity.leagueapplication.data.util.Status
import com.digitalcreativity.leagueapplication.databinding.FragmentTeamsBinding
import com.digitalcreativity.leagueapplication.ui.BaseFragment
import com.digitalcreativity.leagueapplication.ui.MainActivity
import com.digitalcreativity.leagueapplication.ui.competitions.CompetitionsAdapter
import com.digitalcreativity.leagueapplication.util.NetworkHelper
import org.koin.android.ext.android.inject

class TeamsFragment  : BaseFragment(R.layout.fragment_teams) {

    private val TAG = "TeamsFragment"

    private lateinit var viewModel: TeamsViewModel

    private val networkHelper: NetworkHelper by inject()
    private val teamsApi: TeamsApi by inject()
    private val teamsDao: TeamsDao by inject()

    private var _binding: FragmentTeamsBinding? =null
    private val binding: FragmentTeamsBinding get() =_binding!!

    private var listAdapter: TeamsAdapter?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentTeamsBinding.inflate(inflater, container, false)

        (requireActivity() as MainActivity).title = getString(R.string.teams)

        initRecyclerView()

        initViewModel()

        return binding.root
    }


    private fun initRecyclerView() {
        listAdapter = context?.let { TeamsAdapter(it, findNavController()) }
        binding.teamsRecyclerView.adapter = listAdapter
    }

    private fun initViewModel() {

        val viewModelFactory = TeamsViewModel
            .TeamsViewModelFactory(teamsApi, teamsDao)

        viewModel = ViewModelProvider(this, viewModelFactory)[TeamsViewModel::class.java]

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchTeams()

        checkInternetConnection()

    }

    private fun checkInternetConnection() {
        if (networkHelper.isNetworkConnected())
            getTeams()
        else
            getTeamsFromLocal()
    }

    private fun fetchTeams(){
        viewModel.fetchData()
    }

    private fun getTeams() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.getTeams().collect { resource ->
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

    private fun getTeamsFromLocal(){
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.getTeamsFromLocal().collect { list ->

                Log.d(TAG, "getTeamsFromLocal: list = ${list.size}")

                updateUiListComponent(list)
            }
        }

    }

    private fun updateUiListComponent(teamList: List<Team?>?) {
        binding.teamsRecyclerView.apply {
            if (adapter==null)
                adapter = listAdapter

            listAdapter?.submitList(teamList)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}