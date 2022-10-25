package com.example.moviesapp.ui.search

import android.opengl.Visibility
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doBeforeTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.map
import com.example.moviesapp.NavGraphDirections
import com.example.moviesapp.R
import com.example.moviesapp.ViewModelAndRepository.search.SearchViewModel
import com.example.moviesapp.databinding.FragmentSearchBinding
import com.example.moviesapp.roomDatabase.Entity.SearchHistoryEntity
import com.example.moviesapp.roomDatabase.Entity.SearchHistoryEntityWithoutId
import com.example.moviesapp.roomDatabase.RoomViewModel
import com.example.moviesapp.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchFragment: BaseFragment(R.layout.fragment_search) {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModels()
    private val roomViewModel : RoomViewModel by viewModels()
    private val controller = SearchEpoxyController(::onMovieClick)
    private val searchHistoryController = SearchHistoryEpoxyController(::onSearchHistoryClick , ::onCloseClick)

    private var currentText= ""
    private val handler= Handler(Looper.getMainLooper())
    private val searchRunnable= Runnable {
       viewModel.submitQuery(currentText)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding=FragmentSearchBinding.bind(view)


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




        roomViewModel.getSearchHistoryList()

        roomViewModel.searchHistoryListLiveData.observe(viewLifecycleOwner){searchHistoryList->
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
        roomViewModel.insertSearchHistory(
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
        roomViewModel.deleteSearchHistoryByID(movieId)

    }



    //hide main activity toolbar when get into this fragment
    override fun onResume() {
        mainActivity.hideToolbar(true)
        super.onResume()
    }

    //make main activity toolbar visible when leaving this fragment
    override fun onStop() {
        mainActivity.hideToolbar(false)
        super.onStop()
    }







    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}