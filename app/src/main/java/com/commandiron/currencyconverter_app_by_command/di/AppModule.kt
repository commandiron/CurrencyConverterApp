package com.commandiron.currencyconverter_app_by_command.di

import com.commandiron.currencyconverter_app_by_command.repo.ConverterRepository
import com.commandiron.currencyconverter_app_by_command.repo.ConverterRepositoryImpl
import com.commandiron.currencyconverter_app_by_command.service.ConverterAPI
import com.commandiron.currencyconverter_app_by_command.util.Constants
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

    @Singleton
    @Provides
    fun provideConverterApi(): ConverterAPI {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ConverterAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideCryptoRepository(api: ConverterAPI) : ConverterRepository {
        return ConverterRepositoryImpl(api)
    }

}