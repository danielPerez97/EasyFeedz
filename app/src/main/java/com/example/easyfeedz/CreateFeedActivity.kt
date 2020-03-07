package com.example.easyfeedz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.easyfeedz.databinding.ActivityCreateFeedBinding
import com.example.easyfeedz.di.viewmodel.ViewModelFactory
import com.example.easyfeedz.viewmodel.CreateFeedViewModel
import javax.inject.Inject

class CreateFeedActivity : AppCompatActivity()
{
    @Inject lateinit var factory: ViewModelFactory
    lateinit var binding: ActivityCreateFeedBinding
    lateinit var createFeedViewModel: CreateFeedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateFeedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createFeedViewModel = ViewModelProvider(this, factory).get(CreateFeedViewModel::class.java)
    }
}
