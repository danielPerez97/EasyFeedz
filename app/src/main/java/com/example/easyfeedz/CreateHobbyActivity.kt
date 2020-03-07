package com.example.easyfeedz

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.easyfeedz.databinding.ActivityCreateHobbyBinding
import com.example.easyfeedz.di.viewmodel.ViewModelFactory
import com.example.easyfeedz.viewmodel.CreateHobbyViewModel
import javax.inject.Inject

class CreateHobbyActivity: AppCompatActivity()
{
    @Inject lateinit var factory: ViewModelFactory
    private lateinit var binding: ActivityCreateHobbyBinding
    private lateinit var viewModel: CreateHobbyViewModel
    override fun onCreate(savedInstanceState: Bundle?)
    {
        injector().inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityCreateHobbyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, factory).get(CreateHobbyViewModel::class.java)

        binding.createHobbyBtn.setOnClickListener {
            if(binding.hobbyEdit.text.isNotEmpty() && binding.hobbyEdit.text.isNotBlank())
            {
                viewModel.insertHobby(binding.hobbyEdit.text.toString())
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            }
        }
    }
}