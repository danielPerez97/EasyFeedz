package com.example.easyfeedz

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.database.FeedsId
import com.example.easyfeedz.adapter.FeedAdapter
import com.example.easyfeedz.adapter.HobbiesAdapter
import com.example.easyfeedz.databinding.ActivityMainBinding
import com.example.easyfeedz.di.viewmodel.ViewModelFactory
import com.example.easyfeedz.viewmodel.MainViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import javax.inject.Inject


class MainActivity : AppCompatActivity()
{

    @Inject lateinit var factory: ViewModelFactory
    lateinit var viewmodel: MainViewModel
    lateinit var binding: ActivityMainBinding
    lateinit var feedAdapter : FeedAdapter
    private val hobbiesAdapter = HobbiesAdapter()
    private val disposables = CompositeDisposable()

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?)
    {
        injector().inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewmodel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        handleRecyclerView()

        // If the database is empty, force the user to create a hobby
        handleEmpty()

        // Handle Navigation Drawer setup
        val actionBarDrawerToggle = ActionBarDrawerToggle(this, binding.mainDrawer, binding.mainToolbar, R.string.app_name, R.string.app_name )
        binding.mainDrawer.closeDrawers()
        binding.mainDrawer.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()


        // Set up the Feeds RecyclerView
        with(binding.listFeeds)
        {
            adapter = hobbiesAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        // Set up the Navigation Drawer with hobbies
        disposables += viewmodel.sideBarList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.i("MAINACTIVITY", "DATA RECEIVED")
                hobbiesAdapter.setData(it)
            }


        // Add a Feed if needed
        binding.fab.setOnClickListener{
            val i = Intent(this, CreateFeedActivity::class.java)
            startActivity(i)
        }

        // If a hobby is clicked, clear whats on screen and pull data from the database for the clicked
        // hobby.
        hobbiesAdapter.clicks()
            .subscribe {
                feedAdapter.setData(emptyList())
                disposables.clear()
                setupList(it.feedsId)
            }
    }

    private fun setupList(id: FeedsId)
    {
        disposables += viewmodel.data(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                feedAdapter.setData(it)
            }
    }


    private fun handleEmpty()
    {
        if (viewmodel.databaseIsEmpty()) {
            val intent = Intent(this, CreateHobbyActivity::class.java)
            startActivity(intent)
        }
    }

    private fun handleRecyclerView()
    {
        feedAdapter = FeedAdapter()
        binding.recyclerviewFeed.adapter = feedAdapter
        binding.recyclerviewFeed.layoutManager = LinearLayoutManager(baseContext)
    }

}
