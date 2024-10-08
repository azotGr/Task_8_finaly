package com.example.task_8_finaly

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface iTunesAPI {
    @GET("/search")
    fun search(
        @Query("term") searchTerm: String,
        @Query("media") mediaType: String = "music",
        @Query("entity") entityType: String = "song"
    ): Call<ItunesSearchResponse>
}