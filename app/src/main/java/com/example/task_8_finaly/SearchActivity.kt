package com.example.task_8_finaly

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity() {

    private lateinit var searchLine: EditText
    private lateinit var clearButton: AppCompatImageView
    private var recyclerView: RecyclerView? = null
    private lateinit var trackAdapter: TrackAdapter

    private lateinit var iTunesService: iTunesAPI
    private lateinit var placeholderError: ImageView
    private lateinit var messageError: TextView
    private lateinit var errorLayout: LinearLayout
    private lateinit var updateButtonLayout: LinearLayout
    private lateinit var updateButton: Button
    private lateinit var progressBarScreen: LinearLayout

    private lateinit var searchHistoryTracks: SearchHistoryTracks

    private var lastQuery: String = ""


    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }

    private var isClickAllowed = true
    private val handler = Handler(Looper.getMainLooper())

    private fun clickDebounce() : Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
        }
        return current
    }

    private var searchRunnable: Runnable? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_search)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://itunes.apple.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        iTunesService = retrofit.create(iTunesAPI::class.java)

        searchLine = findViewById(R.id.searchLine)
        clearButton = findViewById(R.id.clearIcon)
        recyclerView = findViewById(R.id.recyclerView)
        placeholderError = findViewById(R.id.placeholderError)
        messageError = findViewById(R.id.messageError)
        errorLayout = findViewById(R.id.errorLayout)
        updateButtonLayout = findViewById(R.id.update_button_layout)
        updateButton = findViewById(R.id.update_button)
        progressBarScreen = findViewById(R.id.progressBarLayout)

        recyclerView?.visibility = View.GONE
        errorLayout.visibility = View.GONE
        progressBarScreen.visibility = View.GONE



        val backButton: ImageButton = findViewById(R.id.buttonBackSearch)
        backButton.setOnClickListener {
            finish()
        }

        fun hideKeyboard() {
            val hide = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            hide.hideSoftInputFromWindow(searchLine.windowToken, 0)
        }

        clearButton.setOnClickListener {
            searchLine.text.clear()
            hideKeyboard()
            recyclerView?.visibility = View.GONE
            hideError()
            hideUpdateButton()
            searchHistoryTracks.loadSearchHistory() // Загрузка истории после очистки текста
            trackAdapter.updateTracks(emptyList())
        }

        updateButton.setOnClickListener {
            retryLastSearch()
        }

        trackAdapter = TrackAdapter(emptyList()) { track ->
            if (clickDebounce()) {
                val intent = Intent(this, ActivityPlayer::class.java)
                intent.putExtra("track", track)
                startActivity(intent)
                searchHistoryTracks.addTrackToHistory(track)
                searchHistoryTracks.hideHistory()
            }
        }

        recyclerView?.adapter = trackAdapter
        recyclerView?.layoutManager = LinearLayoutManager(this)

        searchHistoryTracks = SearchHistoryTracks(
            this,
            findViewById(R.id.recyclerHistoryTracks),
            findViewById(R.id.historyClear),
            findViewById(R.id.searchHistoryHeader)
        )

        searchHistoryTracks.setOnItemClickListener { track ->
            startPlayerActivity(track)
        }


        // Настройка событий для поиска
        searchLine.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clearButton.visibility = if (s.isNullOrEmpty()) View.GONE else View.VISIBLE
                if (s.isNullOrEmpty()) {
                    recyclerView?.visibility = View.GONE
                    trackAdapter.updateTracks(emptyList())
                    hideError()
                    hideUpdateButton()
                    if (searchLine.hasFocus()) {
                        searchHistoryTracks.loadSearchHistory() // Показываем историю, если есть
                    }
                } else {
                    searchHistoryTracks.hideHistory() // Скрываем историю при вводе текста
                    progressBarScreen.visibility = View.VISIBLE
                    debounceSearch(s.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) {
                    // Дополнительная проверка в afterTextChanged для обработки случая длительного нажатия кнопки "Стереть"
                    recyclerView?.visibility = View.GONE
                    trackAdapter.updateTracks(emptyList())
                    hideError()
                    hideUpdateButton()
                    if (searchLine.hasFocus()) {
                        searchHistoryTracks.loadSearchHistory()
                    }
                }
            }
        })

        // Отслеживание состояния фокуса
        searchLine.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus && searchLine.text.isEmpty()) {
                searchHistoryTracks.loadSearchHistory()
                recyclerView?.visibility = View.GONE
            } else {
                searchHistoryTracks.hideHistory()
            }
        }

        //обработка пустого запроса от пользователя
        searchLine.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val query = searchLine.text.toString().trim()
                if (query.isNotEmpty()) {
                    doSearch(query)
                    hideKeyboard()
                    searchHistoryTracks.hideHistory()
                } else {
                    recyclerView?.visibility = View.GONE
                    Toast.makeText(this, "Введите непустой запрос", Toast.LENGTH_SHORT).show()
                }
                true
            } else {
                false
            }
        }


        backButton.setOnClickListener {
            finish()
        }

        // Восстановление состояния при создании активити
        if (savedInstanceState != null) {
            lastQuery = savedInstanceState.getString("last_query", "")
            searchLine.setText(lastQuery)
            searchLine.setSelection(lastQuery.length)
            if (searchLine.hasFocus()) {
                if (lastQuery.isEmpty()) {
                    if (searchHistoryTracks.hasHistory()) {
                        recyclerView?.visibility = View.GONE
                        hideError()
                        hideUpdateButton()
                        searchHistoryTracks.loadSearchHistory()
                    } else {
                        recyclerView?.visibility = View.GONE
                        hideError()
                        hideUpdateButton()
                        searchHistoryTracks.hideHistory()
                    }
                } else {
                    doSearch(lastQuery)
                    searchHistoryTracks.hideHistory()
                }
            }
        }

        if (searchLine.text.isEmpty() && !searchLine.hasFocus()) {
            searchHistoryTracks.hideHistory()
        }

        // Проверка наличия
        val sharedPreferences = getSharedPreferences("player_prefs", MODE_PRIVATE)
        val trackJson = sharedPreferences.getString("current_track", null)
        if (trackJson != null) {
            val track = Gson().fromJson(trackJson, Track::class.java)
            val intent = Intent(this, ActivityPlayer::class.java)
            intent.putExtra("track", track)
            startActivity(intent)
        }
    }

    //сохраниние
    override fun onPause() {
        super.onPause()
        val sharedPreferences = getSharedPreferences("player_prefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val track = intent.getSerializableExtra("track") as? Track
        if (track != null) {
            val trackJson = Gson().toJson(track)
            editor.putString("current_track", trackJson)
        } else {
            editor.remove("current_track")
        }
        editor.apply()
    }

    // Сохранение состояния
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("SEARCH_TEXT", searchLine.text.toString())
    }

    // восстановление состояния
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val searchText = savedInstanceState.getString("SEARCH_TEXT")
        searchLine.setText(searchText)
        searchLine.setSelection(searchText?.length ?: 0)
    }

    // Выполнение поискового запроса с задержкой (debounce)
    private fun debounceSearch(query: String) {
        searchRunnable?.let { handler.removeCallbacks(it) }
        searchRunnable = Runnable {
            doSearch(query)
        }
        searchRunnable?.let { runnable ->
            handler.postDelayed(runnable, 2000) // Задержка 2 секунды
        }
    }

    // Выполнение поискового запроса
    private fun doSearch(query: String) {
        lastQuery = query
        searchHistoryTracks.hideHistory()

        hideError()
        hideUpdateButton()
        recyclerView?.visibility = View.GONE
        //progressBarScreen.visibility = View.VISIBLE

        val call = iTunesService.search(query)
        call.enqueue(object : Callback<ItunesSearchResponse> {
            override fun onResponse(
                call: Call<ItunesSearchResponse>,
                response: Response<ItunesSearchResponse>
            ) {
                progressBarScreen.visibility = View.GONE
                if (response.isSuccessful) {
                    val searchResponse = response.body()
                    if (searchResponse != null && searchResponse.results.isNotEmpty()) {
                        val tracks = searchResponse.results
                        trackAdapter.updateTracks(tracks)
                        recyclerView?.visibility = View.VISIBLE
                        Log.d("NOTNULL", "isSuccessful")
                        hideError()
                        hideUpdateButton()
                    } else {
                        noResults()
                        Log.d("NoResultsPlaceholder", "isSuccessful")
                    }
                } else {
                    showError()
                    Log.d("ErrorPlaceholder", "NOTSuccessful")
                }
            }

            override fun onFailure(call: Call<ItunesSearchResponse>, t: Throwable) {
                progressBarScreen.visibility = View.GONE
                showError()
            }
        })
    }

    private fun retryLastSearch() {
        doSearch(lastQuery)
    }

    private fun noResults() {
        trackAdapter.updateTracks(emptyList())
        recyclerView?.visibility = View.GONE
        errorLayout.visibility = View.VISIBLE
        placeholderError.visibility = View.VISIBLE
        messageError.visibility = View.VISIBLE
        updateButtonLayout.visibility = View.GONE
        messageError.text = getString(R.string.nothing_found)
        placeholderError.setImageResource(R.drawable.not_search)
    }

    private fun showError() {
        trackAdapter.updateTracks(emptyList())
        recyclerView?.visibility = View.GONE
        errorLayout.visibility = View.VISIBLE
        placeholderError.visibility = View.VISIBLE
        messageError.visibility = View.VISIBLE
        updateButtonLayout.visibility = View.VISIBLE
        messageError.text = getString(R.string.no_int)
        placeholderError.setImageResource(R.drawable.not_internet)
        searchHistoryTracks.hideHistory()
    }


    private fun hideError() {
        errorLayout.visibility = View.GONE
    }


    private fun hideUpdateButton() {
        updateButtonLayout.visibility = View.GONE
    }


    private fun startPlayerActivity(track: Track) {
        val intent = Intent(this@SearchActivity, ActivityPlayer::class.java)
        intent.putExtra("track", track)
        startActivity(intent)
    }

}
