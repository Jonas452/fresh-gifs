package com.jonas.freshgifs.di

import com.jonas.freshgifs.data.remote.FreshGIFSAPI
import com.jonas.freshgifs.data.repository.FreshGIFSRepository
import com.jonas.freshgifs.data.repository.FreshGIFSRepositoryImpl
import com.jonas.freshgifs.util.Util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideFreshGIFSRemoteDataSource(retrofit: Retrofit): FreshGIFSAPI =
        retrofit.create(FreshGIFSAPI::class.java)


    @Provides
    @Singleton
    fun providesFreshGIFSRepository(impl: FreshGIFSRepositoryImpl): FreshGIFSRepository = impl
}
