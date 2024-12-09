package com.example.task_8_finaly.data.models

import com.example.task_8_finaly.data.dto.TrackDto

class TrackSearchResponse(val resultCount: String, val results: List<TrackDto>): Response() {}