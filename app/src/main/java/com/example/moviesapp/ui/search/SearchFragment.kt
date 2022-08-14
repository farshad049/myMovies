package com.example.moviesapp.ui.search

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.moviesapp.BaseFragment
import com.example.moviesapp.NavGraphDirections
import com.example.moviesapp.R
import com.example.moviesapp.ViewModelAndRepository.search.SearchViewModel
import com.example.moviesapp.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment:BaseFragment(R.layout.fragment_search) {
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


        binding.edSearch.addTextChangedListener{
            if (binding.edSearch.text.toString().trim().isEmpty()){
                binding.tvPleaseType.isVisible=true
                binding.epoxyRecyclerView.isVisible=false
            }else{
                binding.tvPleaseType.isVisible=false
                binding.epoxyRecyclerView.isVisible=true
            }
        }


        binding.epoxyRecyclerView.setControllerAndBuildModels(controller)


        //just after text change this wil work
        binding.edSearch.doAfterTextChanged {
            //set current text to what user typed
            currentText=it?.toString() ?:""
            //remove last thing that are in the que
            handler.removeCallbacks(searchRunnable)
            //run search function after 0.5 second pause which is almost after user stop typing the text
            handler.postDelayed(searchRunnable,500L)

            if (it!!.isEmpty()){
                binding.tvPleaseType.isVisible=true
                binding.epoxyRecyclerView.isVisible=false
            }
        }

        lifecycleScope.launch{
            viewModel.searchFlow.collectLatest {data->
                controller.submitData(data)
            }
        }








    }//FUN

    private fun onMovieClick(movieId: Int) {
        val directions=NavGraphDirections.actionGlobalToMovieDetailFragment(movieId)
        findNavController().navigate(directions)

    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}