package com.w1tty.movies.demo.ui.screens.details

import androidx.navigation.fragment.findNavController
import com.w1tty.movies.demo.R
import com.w1tty.movies.demo.base.BaseFragment
import com.w1tty.movies.demo.model.MoviePm
import com.w1tty.movies.demo.ui.views.StateHolderLayout
import com.w1tty.movies.demo.utils.glideLoadPicture
import com.w1tty.movies.demo.utils.parseFullDate
import com.w1tty.movies.domain.base.Result
import com.w1tty.movies.domain.model.MovieDetail
import kotlinx.android.synthetic.main.fragment_movie_details.*
import java.util.*

class MovieDetailsFragment : BaseFragment<MovieDetailsViewModel>() {
  
  companion object {
    const val EXTRA_MOVIE_ID = "MovieDetailsFragment.movie.id"
  }
  
  override val layoutResId: Int = R.layout.fragment_movie_details
  
  override val viewModel: MovieDetailsViewModel by provideViewModel()
  
  override fun initViews() {
    parseArguments()
    initToolbar()
    initControls()
    subscribeToViewModel()
  }
  
  private fun parseArguments() {
    val movie = arguments?.getParcelable(EXTRA_MOVIE_ID) as? MoviePm
    viewModel.initViewModel(movie!!)
  }
  
  private fun initToolbar() {
    toolbar.navigationClickListener = { findNavController().popBackStack() }
  }
  
  private fun initControls() {
    stateHolderLayout.retryCallback = { viewModel.retryLoad() }
  }
  
  private fun subscribeToViewModel() {
    viewModel.getLoadingLiveData().observe(viewLifecycleOwner) {
      stateHolderLayout.setStateOrNothing(StateHolderLayout.State.LOADING, it)
    }
    viewModel.getMovieLiveData().observe(viewLifecycleOwner) {
      tvTitle.text = it.title
      ivPicture.glideLoadPicture(it.picture)
    }
    viewModel.getMovieDetailLiveData().observe(viewLifecycleOwner) {
      handleMovieDetailResult(it)
    }
  }
  
  private fun handleMovieDetailResult(result: Result<MovieDetail>) {
    when (result.status) {
      Result.Status.ERROR -> {
        stateHolderLayout.setState(StateHolderLayout.State.ERROR)
      }
      Result.Status.SUCCESS -> {
        stateHolderLayout.setState(StateHolderLayout.State.NOTHING)
        result.data?.let {
          tvReleaseDate.text = parseFullDate(Locale.getDefault(), it.releaseDate)
          tvMovieDescription.text = it.overview
        }
      }
    }
  }
}