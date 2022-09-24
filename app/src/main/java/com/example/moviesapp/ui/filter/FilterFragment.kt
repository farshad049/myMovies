package com.example.moviesapp.ui.filter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.moviesapp.BaseFragment
import com.example.moviesapp.MainActivity
import com.example.moviesapp.R
import com.example.moviesapp.ViewModelAndRepository.dashboard.DashboardViewModel
import com.example.moviesapp.ViewModelAndRepository.filter.FilterViewModel
import com.example.moviesapp.databinding.FragmentFilterBinding
import com.example.moviesapp.model.ui.FilterByGenreAndImdbRate
import com.example.moviesapp.model.ui.UiGenreFilter
import com.example.moviesapp.model.ui.UiImdbRateFilter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@AndroidEntryPoint
class FilterFragment:Fragment() {
    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FilterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilterBinding.inflate(inflater , container , false)
        return binding.root
    }





    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val controller= FilterFragmentEpoxyController(viewModel)

        binding.epoxyRecyclerView.setController(controller)


//        viewModel.store.stateFlow.map { it.movieFilterByGenre }.distinctUntilChanged().asLiveData().observe(viewLifecycleOwner){ setOfGenresFilter->
//
//            val data=setOfGenresFilter.genres.map {
//                UiFilter(
//                    filterDisplayName = it,
//                    isSelected = setOfGenresFilter.selectedGenres.contains(it)
//                )
//            }
//
//            controller.setData(data)
//        }


//        combine(
//            viewModel.store.stateFlow.map { it.movieFilterByGenre } ,
//            viewModel.store.stateFlow.map { it.movieFilterByImdb }
//        ) { setOfGenresFilter, setOfImdbFilter ->
//
//            val genreData = setOfGenresFilter.genres.map { genres ->
//                UiGenreFilter(
//                    filterDisplayName = genres,
//                    isSelected = setOfGenresFilter.selectedGenres.contains(genres)
//                )
//            }
//
//            val imdbData = setOfImdbFilter.imdbRate.map { imdbRate ->
//                UiImdbRateFilter(
//                    filterDisplayName = imdbRate,
//                    isSelected = setOfImdbFilter.selectedImdbRate.contains(imdbRate)
//
//                )
//            }
//
//            return@combine FilterByGenreAndImdbRate(genreData , imdbData)
//
//        }.distinctUntilChanged().asLiveData().observe(viewLifecycleOwner){data->
//            controller.setData(data.filteredByGenreList, data.filteredByImdbList)
//        }





        //viewModel.getFilterByGenreInfo()
        //viewModel.getFilterByImdbRateInfo()

        //viewModel.test()

        combine(
            viewModel.filterByGenreInfo1LiveData.asFlow() ,
            viewModel.filterByImdbRateInfo1LiveData.asFlow()
        ) { setOfGenresFilter, setOfImdbFilter ->

            val genreData = setOfGenresFilter.genres.map { genres ->
                UiGenreFilter(
                    filterDisplayName = genres,
                    isSelected = setOfGenresFilter.selectedGenres.contains(genres)
                )
            }

            val imdbData = setOfImdbFilter.imdbRate.map { imdbRate ->
                UiImdbRateFilter(
                    filterDisplayName = imdbRate,
                    isSelected = setOfImdbFilter.selectedImdbRate.contains(imdbRate)

                )
            }

            return@combine FilterByGenreAndImdbRate(genreData , imdbData)

        }.distinctUntilChanged().asLiveData().observe(viewLifecycleOwner){data->
            controller.setData(data.filteredByGenreList, data.filteredByImdbList)
            Log.i("test111" , data.filteredByGenreList.toString())
        }






















    }//FUN



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}