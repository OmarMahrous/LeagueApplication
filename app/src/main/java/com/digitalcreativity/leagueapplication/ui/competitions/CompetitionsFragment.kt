package com.digitalcreativity.leagueapplication.ui.competitions

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.digitalcreativity.leagueapplication.R
import com.digitalcreativity.leagueapplication.databinding.FragmentCompetitionsBinding
import com.google.gson.Gson

class CompetitionsFragment : Fragment(R.layout.fragment_competitions) {

    private val TAG = "CompetitionsFragment"

    private val viewModel: CompetitionsViewModel by viewModels()


    private var _binding: FragmentCompetitionsBinding? =null
    private val binding: FragmentCompetitionsBinding get() =_binding!!

//    private var listAdapter:CategoriesAdapter?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentCompetitionsBinding.inflate(inflater, container, false)

        initRecyclerView()

        return binding.root
    }

    private fun initRecyclerView() {
//        listAdapter = CategoriesAdapter(false)
//        binding.categoriesRecyclerview.adapter = listAdapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        getCategories()


    }

//    private fun getCategories() {
//        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
//            viewModel.sourcesEvents.collect{ event->
//                when(event){
//                    is GetSourcesEvent.GetDataOnSuccess ->{
//                        Log.i(TAG, "onViewCreated: sources size = ${event.sourceList.size}")
//
//                        val categoryList = ListMapper.categoriesFromSources(event.sourceList)
//
//                        updateUiListComponent(categoryList)
//                    }
//                    is GetSourcesEvent.ShowMessageOnError ->
//                        Log.e(TAG, "onViewCreated: fail to load sources ${event.msg}")
//
//                    else -> Log.d(TAG, "onViewCreated: show loading")
//                }
//            }
//        }
//
//    }





//    private fun updateUiListComponent(categoryList: List<Category>) {
//        binding.categoriesRecyclerview.apply {
//            if (adapter==null)
//                adapter = listAdapter
//
//            listAdapter?.submitList(categoryList)
//
//        }
//
//
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}