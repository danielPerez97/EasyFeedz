package com.example.easyfeedz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.database.FeedsId
import com.example.easyfeedz.databinding.ActivityMainBinding
import com.example.easyfeedz.di.viewmodel.ViewModelFactory
import com.example.easyfeedz.model.ViewFeed
import com.example.easyfeedz.viewmodel.MainViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class MainActivity : AppCompatActivity()
{

    @Inject lateinit var factory: ViewModelFactory
    lateinit var viewmodel: MainViewModel
    lateinit var binding: ActivityMainBinding

    lateinit var disposable: Disposable
    lateinit var feedAdapter : FeedAdapter
    lateinit var feedList : MutableList<ViewFeed>

    override fun onCreate(savedInstanceState: Bundle?)
    {
        injector().inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewmodel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        handleRecyclerView()

        viewmodel.database.feedsQueries.insert("Smash")

        disposable = viewmodel.data(viewmodel.database.feedsQueries.selectAll().executeAsList()[0]._id)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { Log.i("MainActivity", "SUBSCRIBED") }
            .doOnNext { Log.i("MainActivity", "RECEIVED DATA") }
            .subscribe {
                feedAdapter.setData(it)
            }
    }

    private fun handleRecyclerView(){
        feedAdapter = FeedAdapter()
        binding.recyclerviewFeed.adapter = feedAdapter
        binding.recyclerviewFeed.layoutManager = LinearLayoutManager(baseContext)
    }
}
