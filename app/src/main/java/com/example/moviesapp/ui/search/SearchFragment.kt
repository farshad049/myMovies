package com.example.moviesapp.ui.search

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.moviesapp.BaseFragment
import com.example.moviesapp.NavGraphDirections
import com.example.moviesapp.R
import com.example.moviesapp.arch.SearchViewModel
import com.example.moviesapp.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

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

        if (binding.edSearch.text.toString().trim().isEmpty()){
            binding.edSearch.hint="Please Enter Some"
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
        }

        lifecycleScope.launch{
            viewModel.searchFlow.collectLatest {
                //at first we set local exception to null, in order to run the epoxy controller(look at epoxy controller code)
               // controller.localException = null
                controller.submitData(it)
            }
        }
        //handing error  state  when get into fragment before user type anything
//        viewModel.localExceptionEventLiveData.observe(viewLifecycleOwner){
//            //if getContent is not null then set the localException State from live data and set it to epoxy display
//            it.getContent()?.let { localException->
//                //handle displaying local exception
//                controller.localException=localException
//            }
//        }







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