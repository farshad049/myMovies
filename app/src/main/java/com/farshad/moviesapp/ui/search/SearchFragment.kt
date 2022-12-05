package com.farshad.moviesapp.ui.search

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.farshad.moviesapp.NavGraphDirections
import com.farshad.moviesapp.databinding.FragmentSearchBinding
import com.farshad.moviesapp.data.db.Entity.SearchHistoryEntity
import com.farshad.moviesapp.ui.MainActivity
import com.farshad.moviesapp.ui.search.epoxy.SearchEpoxyController
import com.farshad.moviesapp.ui.search.epoxy.SearchHistoryEpoxyController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchFragment: Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModels()
    private val searchHistoryViewModel : SearchHistoryViewModel by viewModels()
    private val controller = SearchEpoxyController(::onMovieClick)
    private val searchHistoryController = SearchHistoryEpoxyController(::onSearchHistoryClick , ::onCloseClick , ::onDeleteAllClick)

    private var currentText = ""
    private val handler = Handler(Looper.getMainLooper())
    private val searchRunnable = Runnable {
       viewModel.submitQuery(currentText)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.epoxyRecyclerView.setControllerAndBuildModels(controller)
        binding.historyEpoxyRecyclerView.setController(searchHistoryController)



        //just after text change this wil work
        binding.edSearch.doAfterTextChanged {
            //set current text to what user typed
            currentText=it?.toString() ?:""
            //remove last thing that are in the que
            handler.removeCallbacks(searchRunnable)
            //run search function after 0.5 second pause which is almost after user stop typing the text
            handler.postDelayed(searchRunnable,500L)
            //hide history controller if text in not empty
            binding.historyEpoxyRecyclerView.isVisible = it?.isNotEmpty() == false
            //show history controller if text is empty
            binding.historyEpoxyRecyclerView.isVisible = it?.isEmpty() == true

        }

        lifecycleScope.launch{
            viewModel.searchFlow.collectLatest {data->
                controller.localException = null
                controller.submitData(data)
            }
        }


        viewModel.localExceptionEventLiveData.observe(viewLifecycleOwner){
            //if getContent is not null then set the localException State from live data and set it to epoxy display
            it.getContent()?.let { localException->
                //handle displaying local exception
                controller.localException=localException
            }
        }






        searchHistoryViewModel.searchHistoryListLiveData.observe(viewLifecycleOwner){ searchHistoryList->
            searchHistoryController.setData(searchHistoryList)
        }

        //show history controller if clicked on edit text and text is empty
        binding.edSearch.setOnFocusChangeListener { _, _ ->
            binding.historyEpoxyRecyclerView.isVisible = binding.edSearch.text?.isEmpty() == true
        }















    }//FUN

    private fun onMovieClick(movieId: Int , movieTitle:String) {
        val directions=NavGraphDirections.actionGlobalToMovieDetailFragment(movieId)
        findNavController().navigate(directions)
        //insert searched movie to history list
        searchHistoryViewModel.insertSearchHistory(
            SearchHistoryEntity(
                movieId = movieId,
                movieTitle = movieTitle
            )
        )

    }

    private fun onSearchHistoryClick(movieId: Int){
        findNavController().navigate(NavGraphDirections.actionGlobalToMovieDetailFragment(movieId))

    }

    private fun onCloseClick(movieId : Int){
        searchHistoryViewModel.deleteSearchHistoryByID(movieId)

    }

    private fun onDeleteAllClick(){
        searchHistoryViewModel.deleteAllSearchHistory()
    }



    //hide main activity toolbar when get into this fragment
    override fun onResume() {
        (activity as MainActivity).hideToolbar(true)
        super.onResume()
    }

    //make main activity toolbar visible when leaving this fragment
    override fun onStop() {
        (activity as MainActivity).hideToolbar(false)
        super.onStop()
    }







    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}