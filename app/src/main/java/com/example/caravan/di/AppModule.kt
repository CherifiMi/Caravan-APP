package com.example.caravan.di

import com.example.caravan.data.remote.CaravanApi
import com.example.caravan.data.repository.CaravanRepository
import com.example.caravan.data.util.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePizzaApi(): CaravanApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CaravanApi::class.java)
    }


    @Provides
    @Singleton
    fun provideRepository(api: CaravanApi): CaravanRepository {
        return CaravanRepository(api)
    }
}