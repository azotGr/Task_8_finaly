package com.example.task_8_finaly.creator
import android.content.Context
import com.example.task_8_finaly.data.PlayerRepositoryImpl
import com.example.task_8_finaly.data.SettingsRepositoryImpl
import com.example.task_8_finaly.data.preference.SettingsManager
import com.example.task_8_finaly.data.network.RetrofitNetworkClient
import com.example.task_8_finaly.data.preference.TrackManager
import com.example.task_8_finaly.data.repository.TracksRepositoryImpl
import com.example.task_8_finaly.domain.api.PlayInteractor
import com.example.task_8_finaly.domain.api.TracksInteractor
import com.example.task_8_finaly.domain.api.SettingsInteractor
import com.example.task_8_finaly.domain.api.SettingsRepository
import com.example.task_8_finaly.domain.api.TrackRepository
import com.example.task_8_finaly.domain.impl.PlayInteractorImpl
import com.example.task_8_finaly.domain.impl.SettingsInteractorImpl
import com.example.task_8_finaly.domain.impl.TracksInteractorImpl
import com.example.task_8_finaly.presentation.ui.DefaultUIHandler
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

        fun providePlayInter(): PlayInteractor {
            val playerRepository = PlayerRepositoryImpl()

            // Создаем обработчик UI
            val uiHandler = DefaultUIHandler()

            // Передаём зависимости в PlayImpl
            return PlayInteractorImpl(playerRepository, uiHandler)
        }

        private fun getSettingsManager(context: Context): SettingsManager {
            return SettingsManager(context)
        }

        private fun getSettingsRepository(context: Context): SettingsRepository {
            return SettingsRepositoryImpl(getSettingsManager(context))
        }

        fun provideSettingsInteractor(context: Context): SettingsInteractor {
            return SettingsInteractorImpl(getSettingsRepository(context))
        }

        private fun getTrackManager(context: Context): TrackManager {
            return TrackManager(context)
        }

        private fun getTracksRepository(context: Context): TrackRepository {
            return TracksRepositoryImpl(RetrofitNetworkClient(), getTrackManager(context))
        }


        fun provideTracksInteractor(context: Context): TracksInteractor {
            return TracksInteractorImpl(getTracksRepository(context))
        }


    }
}