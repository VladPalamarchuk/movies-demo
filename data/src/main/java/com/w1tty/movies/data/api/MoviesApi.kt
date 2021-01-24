package com.w1tty.movies.data.api

import com.w1tty.movies.data.api.base.BaseResponseAm
import com.w1tty.movies.data.api.model.MovieAm
import com.w1tty.movies.data.api.model.MovieDetailAm
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {
  
  @GET("3/discover/movie")
  suspend fun getPopularMovies(
    @Query("page") page: Int
  ): Response<BaseResponseAm<List<MovieAm>>>
  
  @GET("3/movie/{ID}")
  suspend fun getMovieDetail(
    @Path("ID") id: String
  ): Response<MovieDetailAm>
  
  @GET("3/search/movie")
  suspend fun searchMovies(
    @Query("page") page: Int,
    @Query("query") query: String
  ): Response<BaseResponseAm<List<MovieAm>>>
}