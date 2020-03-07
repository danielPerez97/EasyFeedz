package com.example.easyfeedz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.easyfeedz.databinding.ActivityCreateFeedBinding
import com.example.easyfeedz.databinding.ActivityMainBinding

class CreateFeedActivity : AppCompatActivity() {
    lateinit var binding: ActivityCreateFeedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
