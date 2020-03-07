package com.example.easyfeedz.viewmodel

import androidx.lifecycle.ViewModel
import com.easyfeedz.database.Database
import javax.inject.Inject

class CreateFeedViewModel @Inject constructor(val database: Database): ViewModel()
{

}