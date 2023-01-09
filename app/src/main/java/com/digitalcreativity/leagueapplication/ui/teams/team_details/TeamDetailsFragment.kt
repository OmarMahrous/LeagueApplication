package com.digitalcreativity.leagueapplication.ui.teams.team_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.digitalcreativity.leagueapplication.R
import com.digitalcreativity.leagueapplication.data.model.Squad
import com.digitalcreativity.leagueapplication.data.source.local.teams.TeamsDao
import com.digitalcreativity.leagueapplication.data.source.remote.teams.team_details.TeamDetailsApi
import com.digitalcreativity.leagueapplication.data.source.remote.teams.team_details.TeamDetailsResponse
import com.digitalcreativity.leagueapplication.data.util.Status
import com.digitalcreativity.leagueapplication.databinding.TeamDetailsScrolledFragmentBinding
import com.digitalcreativity.leagueapplication.ui.util.ImageUrlUtil
import com.digitalcreativity.leagueapplication.util.NetworkHelper
import com.example.stickyheaderbottomsheet.StickyHeaderBottomSheetDialogFragment
import com.squareup.picasso.Picasso
import org.koin.android.ext.android.inject

class TeamDetailsFragment : StickyHeaderBottomSheetDialogFragment() {

    private var _binding: TeamDetailsScrolledFragmentBinding? =null
    private val binding: TeamDetailsScrolledFragmentBinding get() =_binding!!


    private lateinit var viewModel: TeamDetailsViewModel

    private val networkHelper: NetworkHelper by inject()
    private val teamDetailsApi:TeamDetailsApi by inject()
    private val teamsDao: TeamsDao by inject()


    private var listAdapter: SquadPlayersAdapter?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = TeamDetailsScrolledFragmentBinding.inflate(inflater, container, false)

        initRecyclerView()
        initViewModel()

        return binding.root
    }

    override fun getContentRecyclerView(): RecyclerView {

        return binding.teamSquadRecyclerView
    }

    override fun getContentView(): Int {
        return R.layout.team_details_scrolled_fragment
    }

    override fun getHeaderView(): View {
        return binding.teamInfoHeader
    }

    private fun initRecyclerView() {
        listAdapter = context?.let { SquadPlayersAdapter() }
        getContentRecyclerView().adapter = listAdapter
    }

    private fun initViewModel() {

        val viewModelFactory = TeamDetailsViewModel
            .TeamDetailsViewModelFactory(getTeamId(), teamDetailsApi, teamsDao)

        viewModel = ViewModelProvider(this, viewModelFactory)[TeamDetailsViewModel::class.java]

    }

    private fun getTeamId(): Int {
        return arguments?.getInt("teamId") ?:0
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchTeamDetails()

        checkInternetConnection()

    }

    private fun checkInternetConnection() {
        if (networkHelper.isNetworkConnected())
            getTeamDetails()
        else
            getTeamDetailsFromLocal()
    }

    private fun fetchTeamDetails(){
        viewModel.fetchData()
    }

    private fun getTeamDetails() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.getTeamDetails().collect { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {

                        val data = resource.data

                        data?.let {
                            updateDetailUiComponents(it)
                            updateUiListComponent(it.squadPlayers)
                        }


                    }
                    Status.ERROR ->{
//                        context?.let { showErrorMessage(it, resource.message?:"Unknown error")
                        }

                    else -> { // TODO show loading progress }
                    }
                }
            }

        }
    }

    private fun getTeamDetailsFromLocal(){
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.getTeamDetailsFromLocal().collect { team ->


                with(team) {
                    val teamDetails = TeamDetailsResponse(id
                    ,area
                    ,name
                    ,tla
                    ,crestUrl
                    ,address
                    ,phone
                    ,email
                    ,null
                    ,founded
                    ,venue)

                    updateDetailUiComponents(teamDetails)
                }
            }
        }

    }

    private fun updateDetailUiComponents(teamDetailsResponse: TeamDetailsResponse) {

        with(binding){
            team = teamDetailsResponse

            val imageUrl = teamDetailsResponse.crestUrl

            if (imageUrl?.endsWith("svg") == true){
                context?.let { ImageUrlUtil.loadSvgImageFromUrl(it, imageUrl, teamImageView) }
            }else{
                Picasso.get()
                    .load(imageUrl)
                    .placeholder(R.mipmap.ic_placeholder)
                    .into(teamImageView)
            }
        }



    }

    private fun updateUiListComponent(teamPlayerList: List<Squad>?) {

        getContentRecyclerView().apply {
            if (adapter==null)
                adapter = listAdapter

            listAdapter?.submitList(teamPlayerList)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}