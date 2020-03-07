package com.example.easyfeedz.viewmodel

import androidx.lifecycle.ViewModel
import com.easyfeedz.database.Database
import javax.inject.Inject

class CreateHobbyViewModel @Inject constructor(val database: Database): ViewModel()
{
    fun insertHobby(name: String)
    {
        database.feedsQueries.insert(name)
    }
}