package com.example.task_8_finaly.creator
import android.content.Context
import com.example.task_8_finaly.App
import com.example.task_8_finaly.data.PlayerRepositoryImpl
import com.example.task_8_finaly.data.preference.SettingsManager
import com.example.task_8_finaly.data.SharedPreferenceRepositoryImp
import com.example.task_8_finaly.data.network.RetrofitNetworkClient
import com.example.task_8_finaly.data.preference.TrackManager
import com.example.task_8_finaly.data.repository.TracksRepositoryImpl
import com.example.task_8_finaly.domain.api.PlayInteractor
import com.example.task_8_finaly.domain.api.TracksInteractor
import com.example.task_8_finaly.domain.api.SettingsInteractor
import com.example.task_8_finaly.domain.api.SharedPreferenceRepository
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

        fun getSettingInteractor(): SettingsInteractor {
            return SettingsInteractorImpl(getSharedPreferenceRepository())
        }

        fun provideSettingsInteractor(): SettingsInteractor {
            return SettingsInteractorImpl(getSharedPreferenceRepository())
        }
        private fun getSharedPreferenceRepository(): SharedPreferenceRepository {
            return SharedPreferenceRepositoryImp()
        }

        private fun getSettingsManager(): SettingsManager {
            return SettingsManager(App.getContext())
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