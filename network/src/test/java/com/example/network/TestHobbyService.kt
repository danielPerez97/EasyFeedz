package com.example.network

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


class TestHobbyService
{
    private val networkModule = NetworkModule("http://localhost:8080/")

    private val hobbyService: HobbyService = networkModule.provideRetrofit(networkModule.provideOkhttp(), networkModule.provideMoshi())
        .create(HobbyService::class.java)

    @Test
    fun testSerialization()
    {
        val zain: TwitterAccount = hobbyService.zain().blockingFirst()
        val mango: TwitterAccount = hobbyService.mango().blockingFirst()
        val mew2king: TwitterAccount = hobbyService.mew2king().blockingFirst()

        assertEquals("@ZainNaghmi", zain.author)
        assertEquals(3, zain.tweets.size)
        println(zain.tweets[0].tweet)

        assertEquals("@C9Mang0", mango.author)
        assertEquals(3, mango.tweets.size)

        assertEquals("@MVG_Mew2King", mew2king.author)
        assertEquals(mew2king.tweets.size, 3)
    }

    @Test
    fun testNewHobby()
    {
        hobbyService.newHobby(Hobby(-1L,"Vaping")).blockingSubscribe()
        val hobbies: List<Hobby> = hobbyService.allHobbies().blockingFirst()
        println(hobbies.toString())

        assertTrue(hobbies.isNotEmpty())
        assertTrue(hobbies.map { it.name }.contains("Vaping"))
    }

    @Test
    fun testSources()
    {
        val hobby: Hobby = hobbyService.newHobby(Hobby(8, "CSGO")).blockingFirst()
        val hobbySource: Source = hobbyService.newHobbySource(Source(hobby.id, "twitch.tv/csgo")).blockingFirst()
        val hobbySources: List<Source> = hobbyService.getHobbySources().blockingFirst()
//
        assertTrue(hobbySource.url == "twitch.tv/csgo")
        assertTrue(hobbySources.isNotEmpty())
    }
}