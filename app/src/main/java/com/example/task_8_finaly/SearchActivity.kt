package com.example.task_8_finaly

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SearchActivity : AppCompatActivity() {

    private lateinit var searchLine: EditText
    private lateinit var clearButton: AppCompatImageView
    private var recyclerView: RecyclerView? = null
    private lateinit var trackAdapter: TrackAdapter

    private val trackList: List<Track> = listOf(
        Track(
            "Smells Like Teen Spirit",
            "Nirvana",
            "5:01",
            "https://is5-ssl.mzstatic.com/image/thumb/Music115/v4/7b/58/c2/7b58c21a-2b51-2bb2-e59a-9bb9b96ad8c3/00602567924166.rgb.jpg/100x100bb.jpg"
        ),
        Track(
            "Billie Jean",
            "Michael Jackson",
            "4:35",
            "https://is5-ssl.mzstatic.com/image/thumb/Music125/v4/3d/9d/38/3d9d3811-71f0-3a0e-1ada-3004e56ff852/827969428726.jpg/100x100bb.jpg"
        ),
        Track(
            "Stayin' Alive",
            "Bee Gees",
            "4:10",
            "https://is4-ssl.mzstatic.com/image/thumb/Music115/v4/1f/80/1f/1f801fc1-8c0f-ea3e-d3e5-387c6619619e/16UMGIM86640.rgb.jpg/100x100bb.jpg"
        ),
        Track(
            "Whole Lotta Love",
            "Led Zeppelin",
            "5:33",
            "https://is2-ssl.mzstatic.com/image/thumb/Music62/v4/7e/17/e3/7e17e33f-2efa-2a36-e916-7f808576cf6b/mzm.fyigqcbs.jpg/100x100bb.jpg"
        ),
        Track(
            "Sweet Child O'Mine",
            "Guns N' Roses",
            "5:03",
            "https://is5-ssl.mzstatic.com/image/thumb/Music125/v4/a0/4d/c4/a04dc484-03cc-02aa-fa82-5334fcb4bc16/18UMGIM24878.rgb.jpg/100x100bb.jpg"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_search)

        searchLine = findViewById(R.id.searchLine)
        clearButton = findViewById(R.id.clearIcon)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView?.visibility = View.GONE

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
        }

        trackAdapter = TrackAdapter(trackList)
        recyclerView?.adapter = trackAdapter
        recyclerView?.layoutManager = LinearLayoutManager(this)

        searchLine.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clearButton.visibility = if (s.isNullOrEmpty()) View.GONE else View.VISIBLE

                val filteredList = trackList.filter { track ->
                    track.trackName.contains(s.toString(), ignoreCase = true) ||
                            track.artistName.contains(s.toString(), ignoreCase = true)
                }
                trackAdapter.updateTracks(filteredList)

                recyclerView?.visibility = if (s.isNullOrEmpty()) View.GONE else View.VISIBLE
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        backButton.setOnClickListener {
            finish()
        }

        //сохранение состояния при создании активити
        if (savedInstanceState != null) {
            val searchText = savedInstanceState.getString("SEARCH_TEXT")
            searchLine.setText(searchText)
            searchLine.setSelection(searchText?.length ?: 0)
        }

    }

    //сохранение состояния
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

}
