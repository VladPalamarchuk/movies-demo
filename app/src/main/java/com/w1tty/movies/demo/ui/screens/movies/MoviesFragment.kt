package com.w1tty.movies.demo.ui.screens.movies

import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.w1tty.movies.demo.R
import com.w1tty.movies.demo.base.BaseFragment
import com.w1tty.movies.demo.model.MoviePm
import com.w1tty.movies.demo.pagination.PagedAdapterState
import com.w1tty.movies.demo.ui.screens.details.MovieDetailsFragment.Companion.EXTRA_MOVIE_ID
import com.w1tty.movies.demo.ui.screens.movies.list.MoviesAdapter
import com.w1tty.movies.demo.ui.views.CustomSearchView
import com.w1tty.movies.demo.ui.views.StateHolderLayout
import com.w1tty.movies.demo.utils.adjustStateWith
import com.w1tty.movies.demo.utils.hideKeyboard
import kotlinx.android.synthetic.main.fragment_movies.*
import javax.inject.Inject

class MoviesFragment : BaseFragment<MoviesFragmentViewModel>(),
  MoviesAdapter.Callback, CustomSearchView.Callback {
  
  override val layoutResId: Int = R.layout.fragment_movies
  
  override val viewModel: MoviesFragmentViewModel by provideViewModel()
  
  @Inject
  lateinit var adapter: MoviesAdapter
  
  private val onScrollListener: RecyclerView.OnScrollListener = object : RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
      val manager = recyclerView.layoutManager as LinearLayoutManager
      if (manager.findLastVisibleItemPosition() >= adapter.itemCount - 6) {
        viewModel.loadNextPage()
      }
    }
  }
  
  override fun initViews() {
    initList()
    initControls()
    initSearch()
    subscribeToViewModel()
  }
  
  private fun initList() {
    rvMovies.adapter = adapter
    adapter.retryLoadCallback = {
      viewModel.retryLoad()
    }
    adapter.callback = this
  }
  
  private fun initControls() {
    stateHolderLayout.retryCallback = { viewModel.retryLoad() }
  }
  
  private fun initSearch() {
    toolbar.optionsClickListener = {
      viewModel.openSearchView()
    }
    searchView.callback = this
  }
  
  override fun onResume() {
    super.onResume()
    rvMovies.addOnScrollListener(onScrollListener)
  }
  
  override fun onPause() {
    super.onPause()
    rvMovies.removeOnScrollListener(onScrollListener)
  }
  
  private fun subscribeToViewModel() {
    viewModel.getLoadingLiveData().observe(viewLifecycleOwner) {
      stateHolderLayout.setStateOrNothing(StateHolderLayout.State.LOADING, it)
    }
    viewModel.getPageLoadingLiveData().observe(viewLifecycleOwner) {
      rvMovies.post {
        adapter.showNextPageState(if (it) PagedAdapterState.PROGRESS else PagedAdapterState.NOTHING)
      }
    }
    viewModel.getErrorLiveData().observe(viewLifecycleOwner) {
      stateHolderLayout.setStateOrNothing(StateHolderLayout.State.ERROR, it)
    }
    viewModel.getPagedErrorLiveData().observe(viewLifecycleOwner) {
      rvMovies.post {
        adapter.showNextPageState(if (it) PagedAdapterState.ERROR else PagedAdapterState.NOTHING)
      }
    }
    viewModel.getMoviesLiveData().observe(viewLifecycleOwner) {
      adapter.updateData(it, viewLifecycleOwner.lifecycleScope)
    }
    viewModel.getMoviesEmptyLiveData().observe(viewLifecycleOwner) {
      stateHolderLayout.setStateOrNothing(StateHolderLayout.State.EMPTY, it)
      stateHolderLayout.setEmptyText(
        if (viewModel.isSearchMode()) R.string.movies_empty_search else R.string.movies_empty
      )
    }
    viewModel.getSearchViewLiveData().observe(viewLifecycleOwner) {
      searchView.adjustStateWith(it, rvMovies)
    }
  }
  
  override fun onMovieClicked(item: MoviePm) {
    hideKeyboard()
    findNavController(this).navigate(
      R.id.action_moviesFragment_to_movieDetailsFragment,
      bundleOf(EXTRA_MOVIE_ID to item)
    )
  }
  
  override fun onQueryTextChanged(query: String) {
    viewModel.onQueryTextChanged(query)
  }
  
  override fun onCloseSearchView() {
    viewModel.closeSearchView()
  }
}