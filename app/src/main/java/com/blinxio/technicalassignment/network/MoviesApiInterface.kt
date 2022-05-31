package com.blinxio.technicalassignment.network

import com.blinxio.technicalassignment.BuildConfig
import com.blinxio.technicalassignment.home.models.MovieModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApiInterface {

    @GET("${BuildConfig.API_VERSION}discover/movie?api_key=${BuildConfig.API_KEY}")
    suspend fun getListOfMovies(
        @Query("language") language: String = "en-US",
        @Query("sort_by") sort_by: String = "popularity.desc",
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("include_video") includeVideo: Boolean = false,
        @Query("page") page: Int = 1
    ): Response<MovieModel>
}