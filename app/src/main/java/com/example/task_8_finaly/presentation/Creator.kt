package com.example.task_8_finaly.presentation

import com.example.task_8_finaly.data.network.iTunesAPI
import com.example.task_8_finaly.data.repository.TrackReposImpl
import com.example.task_8_finaly.domain.api.PlayInter
import com.example.task_8_finaly.domain.api.SearchTrackInter
import com.example.task_8_finaly.domain.api.TrackRepository
import com.example.task_8_finaly.domain.impl.PlayImpl
import com.example.task_8_finaly.domain.impl.SearchTrackImpl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Creator {
    companion object {

        private fun createRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl("https://itunes.apple.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        fun createITunesAPI(): iTunesAPI {
            val retrofit = createRetrofit()
            return retrofit.create(iTunesAPI::class.java)
        }

        fun createTrackRepos(): TrackRepository {
            return TrackReposImpl(createITunesAPI())
        }

        fun provideSearchTrackInter(): SearchTrackInter {
            val repository = createTrackRepos()
            return SearchTrackImpl(repository)
        }

        fun providePlayInter(): PlayInter {
            return PlayImpl()
        }
    }
}