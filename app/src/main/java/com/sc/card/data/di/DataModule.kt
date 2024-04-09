package com.sc.card.data.di

import android.content.Context
import com.google.firebase.database.FirebaseDatabase
import com.sc.card.data.datasource.RemoteDataSource
import com.sc.card.data.datasource.RemoteDataSourceImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule() {

    @Singleton
    @Provides
    fun provideFirebaseDatabase() : FirebaseDatabase = FirebaseDatabase
        .getInstance("https://cards-8247b-default-rtdb.firebaseio.com/")

    @Singleton
    @Provides
    fun provideRemoteSource(database: FirebaseDatabase) : RemoteDataSource =
        RemoteDataSourceImp(database)
}