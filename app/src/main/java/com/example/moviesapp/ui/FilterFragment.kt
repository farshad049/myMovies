package com.example.moviesapp.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.moviesapp.BaseFragment
import com.example.moviesapp.R
import com.example.moviesapp.ViewModelAndRepository.MovieViewModel
import com.example.moviesapp.databinding.FragmentFilterBinding
import com.example.moviesapp.util.Constants.appliedFilter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterFragment:BaseFragment(R.layout.fragment_filter) {
    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!
    private val filter=appliedFilter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding=FragmentFilterBinding.bind(view)


        binding.checkUk.isChecked=filter.country.contains("UK")
        binding.checkUs.isChecked=filter.country.contains("US")
        binding.checkGenresCrime.isChecked=filter.genres.contains("Crime")
        binding.checkGenresAction.isChecked=filter.genres.contains("Action")
        binding.checkGenresDrama.isChecked=filter.genres.contains("Drama")





        binding.checkUk.setOnCheckedChangeListener { button, isCheck ->
            if (button.isChecked){
                filter.country.add("UK")
            }else{
                filter.country.remove("UK")
            }
        }

        binding.checkUs.setOnCheckedChangeListener { button, isCheck ->
            if (button.isChecked){
                filter.country.add("US")
            }else{
                filter.country.remove("US")
            }
        }

        binding.checkGenresCrime.setOnCheckedChangeListener { button, isCheck ->
            if (button.isChecked){
                filter.genres.add("Crime")
            }else{
                filter.genres.remove("Crime")
            }
        }

        binding.checkGenresDrama.setOnCheckedChangeListener { button, isCheck ->
            if (button.isChecked){
                filter.genres.add("Drama")
            }else{
                filter.genres.remove("Drama")
            }
        }

        binding.checkGenresAction.setOnCheckedChangeListener { button, isCheck ->
            if (button.isChecked){
                filter.genres.add("Action")
            }else{
                filter.genres.remove("Action")
            }
        }





        binding.btnFilter.setOnClickListener {
            findNavController().navigate(FilterFragmentDirections.actionFilterFragmentToMovieList())
        }








    }//FUN



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}