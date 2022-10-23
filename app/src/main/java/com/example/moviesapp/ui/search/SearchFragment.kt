package com.example.moviesapp.ui.search

import android.opengl.Visibility
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.moviesapp.NavGraphDirections
import com.example.moviesapp.R
import com.example.moviesapp.ViewModelAndRepository.search.SearchViewModel
import com.example.moviesapp.databinding.FragmentSearchBinding
import com.example.moviesapp.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchFragment: BaseFragment(R.layout.fragment_search) {
    private var _binding: FragmentSearchBinding? = null
    private val binding by lazy { _binding!! }
    private val viewModel: SearchViewModel by viewModels()
    private val controller=SearchEpoxyController(::onMovieClick)

    private var currentText= ""
    private val handler= Handler(Looper.getMainLooper())
    private val searchRunnable= Runnable {
       viewModel.submitQuery(currentText)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding=FragmentSearchBinding.bind(view)



        binding.epoxyRecyclerView.setControllerAndBuildModels(controller)


        //just after text change this wil work
        binding.edSearch.doAfterTextChanged {
            //set current text to what user typed
            currentText=it?.toString() ?:""
            //remove last thing that are in the que
            handler.removeCallbacks(searchRunnable)
            //run search function after 0.5 second pause which is almost after user stop typing the text
            handler.postDelayed(searchRunnable,500L)


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








    }//FUN

    private fun onMovieClick(movieId: Int) {
        val directions=NavGraphDirections.actionGlobalToMovieDetailFragment(movieId)
        findNavController().navigate(directions)
    }


    override fun onResume() {
        mainActivity.hideToolbar(true)
        super.onResume()
    }

    override fun onStop() {
        mainActivity.hideToolbar(false)
        super.onStop()
    }







    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}