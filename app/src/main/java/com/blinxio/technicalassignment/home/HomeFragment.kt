package com.blinxio.technicalassignment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.blinxio.technicalassignment.R
import com.blinxio.technicalassignment.databinding.FragmentHomeBinding
import com.blinxio.technicalassignment.home.adapters.MoviesAdapter
import com.blinxio.technicalassignment.home.viewmodel.HomeViewModel
import com.blinxio.technicalassignment.network.ApiResult
import com.blinxio.technicalassignment.utils.repeatOnLifecycleStarted
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private var binding: FragmentHomeBinding? = null
    private val homeViewModel by viewModels<HomeViewModel>()

    @Inject
    lateinit var moviesAdapter: MoviesAdapter

    private var snack: Snackbar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.moviesList?.adapter = moviesAdapter
        binding?.moviesList?.layoutManager = LinearLayoutManager(requireContext())

        moviesAdapter.onClickListener = { movieResult ->
            movieResult.id?.let { movieId ->
                findNavController().navigate(HomeFragmentDirections.openMovieDetailsFragment(movieId))
            }
        }

        fetchMoviesList(view)

        binding?.toTop?.setOnClickListener {
            binding?.moviesList?.scrollToPosition(0)
        }
    }

    private fun fetchMoviesList(view: View) {
        repeatOnLifecycleStarted {
            homeViewModel.moviesList.collect { apiResult ->
                binding?.loadingProgressBar?.isVisible = apiResult is ApiResult.Loading
                when (apiResult) {
                    is ApiResult.Success -> moviesAdapter.submitList(apiResult.value?.movieResults)
                    is ApiResult.Error -> {
                        showErrorSnack(view)
                    }
                    else -> {}
                }
            }
        }
    }

    private fun showErrorSnack(view: View) {
        snack = Snackbar.make(
            view, R.string.error_occured_message, Snackbar.LENGTH_INDEFINITE
        ).setAction(R.string.error_retry_option) { homeViewModel.getMovies() }
        snack!!.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        snack?.dismiss()
        snack = null
    }
}