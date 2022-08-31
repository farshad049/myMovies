package com.example.moviesapp.ui.filter

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import com.example.moviesapp.BaseFragment
import com.example.moviesapp.R
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
class FilterFragment:BaseFragment(R.layout.fragment_filter) {
    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FilterViewModel by viewModels()





    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding=FragmentFilterBinding.bind(view)



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


        combine(
            viewModel.store.stateFlow.map { it.movieFilterByGenre } ,
            viewModel.store.stateFlow.map { it.movieFilterByImdb }
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
        }




















    }//FUN



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}