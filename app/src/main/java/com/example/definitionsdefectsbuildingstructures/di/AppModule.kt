package com.example.definitionsdefectsbuildingstructures.di

import com.example.definitionsdefectsbuildingstructures.data.Repository
import com.example.definitionsdefectsbuildingstructures.data.RepositoryInterface
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {

    @Singleton
    @Binds
    fun provideRepository(repository: Repository): RepositoryInterface

    companion object {}
}