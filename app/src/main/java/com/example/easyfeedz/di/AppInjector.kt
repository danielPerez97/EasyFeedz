package com.example.easyfeedz.di

import com.example.easyfeedz.MainActivity
import com.example.easyfeedz.di.viewmodel.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DatabaseModule::class, ViewModelModule::class])
interface AppInjector
{
    fun inject(activity: MainActivity)
}