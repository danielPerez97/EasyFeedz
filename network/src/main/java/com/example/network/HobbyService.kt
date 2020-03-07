package com.example.network

import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface HobbyService
{
    // Grab tweets related to certain smashers
    @GET("/smash/zain/")
    fun zain(): Observable<TwitterAccount>

    @GET("/smash/mang0/")
    fun mango(): Observable<TwitterAccount>

    @GET("/smash/mew2king/")
    fun mew2king(): Observable<TwitterAccount>

    // Grab all Smasher Tweets
    @GET("/smash/")
    fun allSmash(): Observable<List<TwitterAccount>>

    // Get all the hobbies on the server
    @GET("/hobby/")
    fun allHobbies(): Observable<List<Hobby>>

    // Create a new hobby
    @POST("/hobby/new/")
    fun newHobby(@Body hobby: Hobby): Observable<Hobby>

    // Get all the hobbies on the server
    @GET("/hobby/source")
    fun getHobbySources(): Observable<List<Source>>

    // POST a new hobby
    @POST("/hobby/source/new/")
    fun newHobbySource( @Body source: Source  ): Observable<Source>


}