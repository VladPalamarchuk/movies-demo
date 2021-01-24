package com.w1tty.movies.demo.ui.screens.details

import androidx.lifecycle.*
import com.w1tty.movies.demo.base.BaseViewModel
import com.w1tty.movies.demo.model.MoviePm
import com.w1tty.movies.domain.base.Result
import com.w1tty.movies.domain.interactor.GetMovieDetailsUseCase
import com.w1tty.movies.domain.model.MovieDetail
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(
  private val getMovieDetailsUseCase: GetMovieDetailsUseCase
) : BaseViewModel() {
  
  private val movieLiveData = MutableLiveData<MoviePm>()
  private val loadingLiveData = MutableLiveData<Boolean>()
  
  private val movieDetailLiveData = movieLiveData.switchMap {
    loadingLiveData.value = true
    val params = GetMovieDetailsUseCase.Params(it.id, true)
    liveData {
      getMovieDetailsUseCase.execute(params).collect { result ->
        loadingLiveData.value = false
        emit(result)
      }
    }
  }
  
  fun initViewModel(movie: MoviePm) {
    movieLiveData.value = movie
  }
  
  fun getLoadingLiveData(): LiveData<Boolean> = loadingLiveData
  fun getMovieLiveData(): LiveData<MoviePm> = movieLiveData
  fun getMovieDetailLiveData(): LiveData<Result<MovieDetail>> = movieDetailLiveData
  
  fun retryLoad() {
    loadingLiveData.value = true
    
    val movieId = movieLiveData.value?.id.orEmpty()
    val params = GetMovieDetailsUseCase.Params(movieId, false)
    
    viewModelScope.launch {
      getMovieDetailsUseCase.execute(params).collect {
        loadingLiveData.value = false
        (movieDetailLiveData as MutableLiveData).value = it
      }
    }
  }
}