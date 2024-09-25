package com.example.task_8_finaly
import android.content.Context
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SearchHistoryTracks(
    private val context: Context,
    private val historyRecyclerView: RecyclerView,
    private val clearHistoryButton: MaterialButton,
    private val historyHeader: View
) {

    private val sharedPreferences =
        context.getSharedPreferences("search_history", Context.MODE_PRIVATE)
    private val historyAdapter: TrackAdapter
    private var onItemClickListener: ((Track) -> Unit)? = null

    init {
        historyAdapter = TrackAdapter(emptyList()) { track ->
            addTrackToHistory(track)
            startPlayerActivity(track)
        }
        historyRecyclerView.adapter = historyAdapter
        historyRecyclerView.layoutManager = LinearLayoutManager(context)

        clearHistoryButton.setOnClickListener {
            clearHistory()
        }

        loadSearchHistory()
    }

    //добавление трека в историю поиска
    fun addTrackToHistory(track: Track) {
        val history = getSearchHistory().toMutableList()
        history.remove(track) // Удаляем трек, если он уже есть в истории
        history.add(0, track) // Добавляем трек в начало списка

        if (history.size > 10) {
            history.removeAt(history.size - 1) // Ограничиваем размер истории 10 треками
        }

        saveSearchHistory(history)
        loadSearchHistory()
    }

    //получение истории поиска
    private fun getSearchHistory(): List<Track> {
        val json = sharedPreferences.getString("history", null) ?: return emptyList()
        val type = object : TypeToken<List<Track>>() {}.type
        return Gson().fromJson(json, type)
    }

    //сохранение истории поиска
    private fun saveSearchHistory(history: List<Track>) {
        val json = Gson().toJson(history)
        sharedPreferences.edit().putString("history", json).apply()
    }

    //загрузка и отображение истории поиска
    fun loadSearchHistory() {
        if (hasHistory()) {
            val history = getSearchHistory()
            historyAdapter.updateTracks(history)
            historyRecyclerView.visibility = View.VISIBLE
            clearHistoryButton.visibility = View.VISIBLE
            historyHeader.visibility = View.VISIBLE
        } else {
            hideHistory()
        }
    }

    fun hasHistory(): Boolean {
        return getSearchHistory().isNotEmpty()
    }

    fun hideHistory() {
        historyRecyclerView.visibility = View.GONE
        clearHistoryButton.visibility = View.GONE
        historyHeader.visibility = View.GONE
    }

    private fun clearHistory() {
        saveSearchHistory(emptyList())
        hideHistory()
    }

    fun setOnItemClickListener(listener: (Track) -> Unit) {
        this.onItemClickListener = listener
    }

    private fun startPlayerActivity(track: Track) {
        val intent = Intent(context, ActivityPlayer::class.java)
        intent.putExtra("track", track)
        context.startActivity(intent)
    }
}