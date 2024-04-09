package com.sc.card.domain.di

import com.sc.card.data.datasource.RemoteDataSource
import com.sc.card.domain.repository.UserRepository
import com.sc.card.domain.usecase.LoginUseCase
import com.sc.card.domain.usecase.LoginUserCaseImp
import com.sc.card.domain.usecase.RegisterUseCase
import com.sc.card.domain.usecase.RegisterUseCaseImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Singleton
    @Provides
    fun provideRepository(remote: RemoteDataSource) : UserRepository = UserRepository(remote)

    @Provides
    fun provideLoginUseCase(repository: UserRepository): LoginUseCase = LoginUserCaseImp(repository)

    @Provides
    fun provideRegisterUseCase(repository: UserRepository): RegisterUseCase = RegisterUseCaseImp(repository)
}