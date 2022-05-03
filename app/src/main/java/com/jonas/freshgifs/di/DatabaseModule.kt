package com.jonas.freshgifs.di

import android.content.Context
import com.jonas.freshgifs.data.local.FreshGIFDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun providesFreshGIFDatabase(@ApplicationContext context: Context) =
        FreshGIFDatabase.newInstance(context)

    @Provides
    @Singleton
    fun providesFavoriteGIFDao(db: FreshGIFDatabase) = db.getFavoriteGIFDao()
}
