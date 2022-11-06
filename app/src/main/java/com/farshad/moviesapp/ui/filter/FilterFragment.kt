package com.farshad.moviesapp.ui.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import com.farshad.moviesapp.ViewModelAndRepository.filter.FilterViewModel
import com.farshad.moviesapp.databinding.FragmentFilterBinding
import com.farshad.moviesapp.model.ui.FilterByGenreAndImdbRate
import com.farshad.moviesapp.model.ui.UiFilter
import com.farshad.moviesapp.util.Constants.GENRE
import com.farshad.moviesapp.util.Constants.IMDBRATE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged

@AndroidEntryPoint
class FilterFragment:Fragment() {
    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FilterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterBinding.inflate(inflater , container , false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val controller= FilterFragmentEpoxyController(viewModel)


        combine(
            viewModel.filterByGenreInfoLiveData ,
            viewModel.filterByImdbRateInfoLiveData ,
            viewModel.expandItemsMutableLiveData
        ) { setOfGenresFilter, setOfImdbFilter , setOfExpandedItems ->

            val genreData = setOfGenresFilter.genres.map { genres ->
                UiFilter(
                    isExpand = setOfExpandedItems.setOfExpandIds.contains(GENRE) ,
                    filterInfo = UiFilter.FilterInfo(
                        filterDisplayName = genres,
                        isSelected = setOfGenresFilter.selectedGenres.contains(genres)
                    )
                )
            }

            val imdbData = setOfImdbFilter.imdbRate.map { imdbRate ->
                UiFilter(
                    isExpand = setOfExpandedItems.setOfExpandIds.contains(IMDBRATE) ,
                    filterInfo = UiFilter.FilterInfo(
                        filterDisplayName = imdbRate,
                        isSelected = setOfImdbFilter.selectedImdbRate.contains(imdbRate)
                    )
                )
            }

            return@combine FilterByGenreAndImdbRate(genreData , imdbData)

        }.distinctUntilChanged().asLiveData().observe(viewLifecycleOwner){data->
            controller.setData(data)
        }

        binding.epoxyRecyclerView.setController(controller)






















    }//FUN



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}