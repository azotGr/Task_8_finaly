package com.example.task_8_finaly

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    private var lastQuery: String = ""

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
        recyclerView?.visibility = View.GONE
        errorLayout.visibility = View.GONE


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
        }

        updateButton.setOnClickListener {
            retryLastSearch()
        }

        trackAdapter = TrackAdapter(emptyList())
        recyclerView?.adapter = trackAdapter
        recyclerView?.layoutManager = LinearLayoutManager(this)
        

        searchLine.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clearButton.visibility = if (s.isNullOrEmpty()) View.GONE else View.VISIBLE

                if (s.isNullOrEmpty()) {
                    recyclerView?.visibility = View.GONE
                    hideError()
                    hideUpdateButton()
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        searchLine.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                DoSearch(searchLine.text.toString())
                hideKeyboard()
                true
            } else {
                false
            }
        }


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

    private fun DoSearch(query: String) {
        lastQuery = query
        val call = iTunesService.search(query)
        call.enqueue(object : Callback<ItunesSearchResponse> {
            override fun onResponse(
                call: Call<ItunesSearchResponse>,
                response: Response<ItunesSearchResponse>
            ) {
                if (response.isSuccessful) {
                    val searchResponse = response.body()
                    if (searchResponse != null && searchResponse.results.isNotEmpty()) {
                        val tracks = searchResponse.results
                        trackAdapter.updateTracks(tracks)
                        recyclerView?.visibility = View.VISIBLE
                        hideError()
                        hideUpdateButton()
                    } else {
                        NoResults()
                    }
                } else {
                    showError()
                }
            }

            override fun onFailure(call: Call<ItunesSearchResponse>, t: Throwable) {
                showError()
            }
        })
    }

    private fun retryLastSearch() {
        DoSearch(lastQuery)
    }

    private fun NoResults() {
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
    }


    private fun hideError() {
        errorLayout.visibility = View.GONE
    }


    private fun hideUpdateButton() {
        updateButtonLayout.visibility = View.GONE
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
