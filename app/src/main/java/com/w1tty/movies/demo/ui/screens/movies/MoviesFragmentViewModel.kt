package com.w1tty.movies.demo.ui.screens.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.w1tty.movies.demo.base.BaseViewModel
import com.w1tty.movies.demo.mapper.MoviesPmMapper
import com.w1tty.movies.demo.model.MoviePm
import com.w1tty.movies.demo.pagination.PaginateHelper
import com.w1tty.movies.domain.base.Result
import com.w1tty.movies.domain.base.withoutParams
import com.w1tty.movies.domain.interactor.GetCachedMoviesUseCase
import com.w1tty.movies.domain.interactor.GetMoviesUseCase
import com.w1tty.movies.domain.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MoviesFragmentViewModel @Inject constructor(
  private val getMoviesUseCase: GetMoviesUseCase,
  private val getCachedMoviesUseCase: GetCachedMoviesUseCase,
  private val mapper: MoviesPmMapper,
  private val paginateHelper: PaginateHelper
) : BaseViewModel() {
  
  private var job: Job? = null
  
  private var displayedMovies = listOf<MoviePm>()
  private var searchQuery: String = ""
  
  private val loadingLiveData = MutableLiveData<Boolean>()
  private val errorLiveData = MutableLiveData<Boolean>()
  private val moviesLiveData = MutableLiveData<List<MoviePm>>()
  private val moviesEmptyLiveData = MutableLiveData<Boolean>()
  private val pageLoadingLiveData = MutableLiveData<Boolean>()
  private val pageErrorLiveData = MutableLiveData<Boolean>()
  private val searchViewLiveData = MutableLiveData<Boolean>()
  
  init {
    initialLoad()
  }
  
  fun getLoadingLiveData(): LiveData<Boolean> = loadingLiveData
  fun getPageLoadingLiveData(): LiveData<Boolean> = pageLoadingLiveData
  fun getErrorLiveData(): LiveData<Boolean> = errorLiveData
  fun getPagedErrorLiveData(): LiveData<Boolean> = pageErrorLiveData
  fun getMoviesLiveData(): LiveData<List<MoviePm>> = moviesLiveData
  fun getMoviesEmptyLiveData(): LiveData<Boolean> = moviesEmptyLiveData
  fun getSearchViewLiveData(): LiveData<Boolean> = searchViewLiveData
  
  fun loadNextPage() {
    if (paginateHelper.isNextPageReadyToLoad()) {
      loadCurrentPage()
    }
  }
  
  fun retryLoad() {
    loadCurrentPage()
  }
  
  private fun isEmpty() = displayedMovies.isEmpty()
  
  private fun initialLoad() {
    showLoader()
    viewModelScope.launch {
      val cacheMovies = getCachedMoviesUseCase.execute(withoutParams())
      val movies = cacheMovies.data.orEmpty()
      handleInitialLoad(movies)
      loadCurrentPage()
    }
  }
  
  private suspend fun handleInitialLoad(newMovies: List<Movie>) {
    if (newMovies.isNotEmpty()) {
      displayedMovies = withContext(Dispatchers.Default) {
        mapper.mapList(newMovies)
      }
      loadingLiveData.value = false
      moviesLiveData.value = displayedMovies
    }
  }
  
  private fun loadCurrentPage() {
    hideErrors()
    showLoader()
    
    job?.cancel()
    job = viewModelScope.launch {
      val firstPage = paginateHelper.isFirstPage()
      val params = GetMoviesUseCase.Params(paginateHelper.getCurrentPage(), searchQuery)
      val result = getMoviesUseCase.execute(params)
      
      if (result.status == Result.Status.ERROR) {
        hideLoaders()
        showError()
      }
      if (result.status == Result.Status.SUCCESS) {
        hideErrors()
        handlePageLoadedSucceed(result.data.orEmpty(), firstPage)
      }
    }
  }
  
  private suspend fun handlePageLoadedSucceed(newMovies: List<Movie>, firstPage: Boolean) {
    displayedMovies = withContext(Dispatchers.Default) {
      mergeMoviesAndMap(newMovies, firstPage)
    }
    
    loadingLiveData.value = false
    moviesLiveData.value = displayedMovies
    moviesEmptyLiveData.value = displayedMovies.isEmpty()
    
    paginateHelper.notifyPageLoaded(newMovies.size)
  }
  
  private fun mergeMoviesAndMap(newMovies: List<Movie>, firstPage: Boolean): MutableList<MoviePm> {
    val result = mutableListOf<MoviePm>()
    if (!firstPage) {
      // add previously loaded items
      result.addAll(displayedMovies)
    }
    result.addAll(mapper.mapList(newMovies))
    return result
  }
  
  private fun showLoader() {
    loadingLiveData.value = isEmpty()
    pageLoadingLiveData.value = !isEmpty()
  }
  
  private fun showError() {
    errorLiveData.value = isEmpty()
    pageErrorLiveData.value = !isEmpty()
  }
  
  private fun hideLoaders() {
    loadingLiveData.value = false
    pageLoadingLiveData.value = false
  }
  
  private fun hideErrors() {
    errorLiveData.value = false
    pageErrorLiveData.value = false
  }
  
  fun openSearchView() {
    val isOpened = searchViewLiveData.value ?: false
    if (!isOpened) {
      searchViewLiveData.value = true
    }
  }
  
  fun closeSearchView() {
    if (searchViewLiveData.value == true) {
      searchViewLiveData.value = false
    }
  }
  
  fun onQueryTextChanged(query: String) {
    this.searchQuery = query
    reset()
  }
  
  private fun reset() {
    this.displayedMovies = emptyList()
    this.moviesLiveData.value = emptyList()
    paginateHelper.restart()
    loadCurrentPage()
  }
  
  fun isSearchMode(): Boolean {
    return !searchQuery.isBlank()
  }
}