package com.blinxio.technicalassignment.di

import com.blinxio.technicalassignment.BuildConfig
import com.blinxio.technicalassignment.network.MoviesApiInterface
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideMoshiConverterFactory(): MoshiConverterFactory =
        MoshiConverterFactory.create().asLenient()

    @Provides
    @Singleton
    fun provideRetrofit(
        moshiConverterFactory: MoshiConverterFactory
    ): Retrofit =
        Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).addConverterFactory(moshiConverterFactory)
            .build()

    @Provides
    @Reusable
    fun provideMoviesApiInterface(retrofit: Retrofit): MoviesApiInterface = retrofit.create()
}