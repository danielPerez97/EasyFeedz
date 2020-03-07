package com.example.easyfeedz.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.easyfeedz.viewmodel.CreateFeedViewModel
import com.example.easyfeedz.viewmodel.CreateHobbyViewModel
import com.example.easyfeedz.viewmodel.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule
{
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun cookViewModel(viewModel: MainViewModel?): ViewModel?

    @Binds
    @IntoMap
    @ViewModelKey(CreateFeedViewModel::class)
    abstract fun createFeedViewModel(viewModel: CreateFeedViewModel?): ViewModel?

    @Binds
    @IntoMap
    @ViewModelKey(CreateHobbyViewModel::class)
    abstract fun createHobbyViewModel(viewModel: CreateHobbyViewModel?): ViewModel?
}