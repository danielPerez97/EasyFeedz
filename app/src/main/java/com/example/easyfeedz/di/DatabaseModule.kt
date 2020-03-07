package com.example.easyfeedz.di

import com.easyfeedz.database.Database
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule(private val database: Database)
{
    @Provides
    @Singleton
    fun provideDatabase(): Database = database
}