package com.example.task_8_finaly.data.dto

import com.example.playlist_maker.data.dto.TrackDto
import com.google.gson.annotations.SerializedName

data class ItunesSearchResponse(
    @SerializedName("resultCount") val resultCount: Int,
    @SerializedName("results") val results: List<TrackDto>
)