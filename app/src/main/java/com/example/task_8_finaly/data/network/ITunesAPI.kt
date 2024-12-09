package com.example.task_8_finaly.data.network

import com.example.task_8_finaly.data.dto.TrackSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ItunesApi {

    @GET("/search?entity=song ")

    fun searchTracks(@Query("term") text: String): Call<TrackSearchResponse>
}