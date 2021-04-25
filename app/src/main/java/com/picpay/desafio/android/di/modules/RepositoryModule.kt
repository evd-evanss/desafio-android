package com.picpay.desafio.android.di.modules

import com.picpay.desafio.android.data.repository.ContactsRepository
import com.picpay.desafio.android.data.repository.ContactsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Singleton
    @Binds
    fun userRepository(impl: ContactsRepositoryImpl): ContactsRepository
}