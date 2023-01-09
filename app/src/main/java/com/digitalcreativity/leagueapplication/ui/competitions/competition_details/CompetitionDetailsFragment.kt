package com.digitalcreativity.leagueapplication.ui.competitions.competition_details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.digitalcreativity.leagueapplication.R
import com.digitalcreativity.leagueapplication.data.model.CurrentSeason
import com.digitalcreativity.leagueapplication.data.source.local.competitions.CompetitionsDao
import com.digitalcreativity.leagueapplication.data.source.remote.competitions.details.CompetitionDetailsApi
import com.digitalcreativity.leagueapplication.data.source.remote.competitions.details.CompetitionDetailsResponse
import com.digitalcreativity.leagueapplication.data.util.Status
import com.digitalcreativity.leagueapplication.databinding.FragmentCompetitionDetailsBinding
import com.digitalcreativity.leagueapplication.ui.BaseFragment
import com.digitalcreativity.leagueapplication.ui.MainActivity
import com.digitalcreativity.leagueapplication.ui.util.ScreensNavigator
import com.digitalcreativity.leagueapplication.util.NetworkHelper
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import org.koin.android.ext.android.inject

class CompetitionDetailsFragment : BaseFragment(R.layout.fragment_competition_details) {

    private val TAG = "CompetitionDetailsFragm"

    private lateinit var viewModel: CompetitionDetailsViewModel

    private val networkHelper: NetworkHelper by inject()
    private val competitionDetailsApi: CompetitionDetailsApi by inject()
    private val competitionsDao: CompetitionsDao by inject()

    private var _binding: FragmentCompetitionDetailsBinding? =null
    private val binding: FragmentCompetitionDetailsBinding get() =_binding!!

    private var seasonListAdapter: SeasonsAdapter?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentCompetitionDetailsBinding.inflate(inflater, container, false)

        binding.showTeamsBtn.setOnClickListener {
            ScreensNavigator.navigateToTeamsPage(findNavController())
        }

        initRecyclerView()

        initViewModel()

        return binding.root
    }


    private fun initRecyclerView() {
        seasonListAdapter = context?.let { SeasonsAdapter(it) }
        binding.seasonsRecyclerView.adapter = seasonListAdapter
    }

    private fun initViewModel() {

        val viewModelFactory = CompetitionDetailsViewModel
            .CompetitionDetailsViewModelFactory(2000,competitionDetailsApi, competitionsDao)

        viewModel = ViewModelProvider(this, viewModelFactory)[CompetitionDetailsViewModel::class.java]

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchCompetitionDetails()


        checkInternetConnection()

    }

    private fun checkInternetConnection() {
        if (networkHelper.isNetworkConnected())
            getCompetitionDetails()
        else {
            getCompetitionDetailsFromLocal()
            getSeasonsFromLocal()
        }
    }

    private fun fetchCompetitionDetails(){
        viewModel.fetchData()
    }

    private fun getCompetitionDetails() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.getCompetitionDetails().collect { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {

                        val data = resource.data

                        updateUiComponents(data)
                        updateSeasonsUiList(data?.seasons)


                        Log.d(TAG, "getCompetitionDetails: " +
                                "detail = ${Gson().toJson(data)}")
                    }
                    Status.ERROR ->
                        context?.let { showErrorMessage(it, resource.message?:"Unknown error") }

                    else -> { // TODO show loading progress }
                    }
                }
            }

        }
    }

    private fun getCompetitionDetailsFromLocal(){
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.getCompetitionDetailsFromLocal().collect { details ->

                with( details){
                    val competitionDetails = CompetitionDetailsResponse(
                        id, area,
                        name,
                        code,
                        emblemUrl,
                        plan,
                        currentSeason,
                        null,
                        numberOfAvailableSeasons,
                        lastUpdated
                    )

                    updateUiComponents(competitionDetails)
                }



            }
        }
    }

    private fun getSeasonsFromLocal(){
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.getSeasonsFromLocal().collect { seasons ->

                updateSeasonsUiList(seasons)

            }
        }
    }

    private fun updateUiComponents(competitionDetail: CompetitionDetailsResponse?) {

        (requireActivity() as MainActivity).title = competitionDetail?.name


        with(binding){
            this.competitionDetail = competitionDetail

            try {

                Picasso.get()

                    .load(competitionDetail?.emblemUrl)
                    .placeholder(R.mipmap.ic_placeholder)
                    .into(competitionImageView)
            }catch (e:Exception){
                Log.e(TAG, "bind: error load image : ${e.message}" )
            }
        }
    }

    private fun updateSeasonsUiList(seasonList: List<CurrentSeason>?) {
        binding.seasonsRecyclerView.apply {
            if (adapter==null)
                adapter = seasonListAdapter

            seasonListAdapter?.submitList(seasonList)

        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}