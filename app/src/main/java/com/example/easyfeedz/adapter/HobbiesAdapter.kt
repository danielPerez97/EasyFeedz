package com.example.easyfeedz.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.contentValuesOf
import androidx.recyclerview.widget.RecyclerView
import com.example.easyfeedz.databinding.HobbyTextEntryBinding
import com.example.easyfeedz.model.ViewHobby
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


class HobbiesAdapter() : RecyclerView.Adapter<HobbiesAdapter.ViewHolder>()
{
    private var data: List<ViewHobby> = emptyList()
    private val clicksSubject = PublishSubject.create<ViewHobby>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HobbiesAdapter.ViewHolder
    {
        val inflater = LayoutInflater.from(parent.context)
        val binding = HobbyTextEntryBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: HobbiesAdapter.ViewHolder, position: Int)
    {
        holder.bind(data[position])
    }

    fun setData(newData: List<ViewHobby>)
    {
        data = newData
        notifyDataSetChanged()
    }

    fun clicks(): Observable<ViewHobby>
    {
        return clicksSubject
    }

    inner class ViewHolder(private val binding: HobbyTextEntryBinding): RecyclerView.ViewHolder(binding.root)
    {
        fun bind(hobby: ViewHobby)
        {
            binding.hobbyTv.text = hobby.name
            binding.root.setOnClickListener {
                clicksSubject.onNext(hobby)
            }
        }
    }

}