package com.blinxio.technicalassignment.network

import com.blinxio.technicalassignment.BuildConfig
import com.blinxio.technicalassignment.details.models.MovieDetails
import com.blinxio.technicalassignment.home.models.MovieModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApiInterface {

    companion object {
        private const val LANGUAGE_QUERY = "language"
        private const val SORT_BY_QUERY = "sort_by"
        private const val INCLUDE_ADULT_QUERY = "include_adult"
        private const val INCLUDE_VIDEO_QUERY = "include_video"
        private const val PAGE_QUERY = "page"
        private const val DISCOVER_PATH = "discover"
        private const val MOVIE_PATH = "movie"
        private const val API_KEY_PATH = "api_key"
        private const val MOVIE_ID = "movieId"
    }

    @GET("${BuildConfig.API_VERSION}$DISCOVER_PATH/$MOVIE_PATH?$API_KEY_PATH=${BuildConfig.API_KEY}")
    suspend fun getListOfMovies(
        @Query(LANGUAGE_QUERY) language: String = "en-US",
        @Query(SORT_BY_QUERY) sortBy: String = "popularity.desc",
        @Query(INCLUDE_ADULT_QUERY) includeAdult: Boolean = false,
        @Query(INCLUDE_VIDEO_QUERY) includeVideo: Boolean = false,
        @Query(PAGE_QUERY) page: Int = 1
    ): Response<MovieModel>

    @GET("${BuildConfig.API_VERSION}$MOVIE_PATH/{$MOVIE_ID}?$API_KEY_PATH=${BuildConfig.API_KEY}")
    suspend fun getMovieDetails(
        @Path(MOVIE_ID) movieId: Int? = null
    ): Response<MovieDetails>
}