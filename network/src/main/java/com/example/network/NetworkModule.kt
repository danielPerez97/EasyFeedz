package com.example.network

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule(private val baseUrl: String)
{
    @Provides
    @Singleton
    fun provideOkhttp(): OkHttpClient
    {
        return OkHttpClient.Builder()
            .build()
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi
    {
        return Moshi.Builder()
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit
    {
        return Retrofit.Builder()
            .client( okHttpClient )
            .baseUrl( baseUrl )
            .addCallAdapterFactory( RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()) )
            .addConverterFactory( MoshiConverterFactory.create(moshi) )
            .build()
    }
}