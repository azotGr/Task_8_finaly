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

class SearchActivity : AppCompatActivity() {

    private lateinit var searchLine: EditText
    private lateinit var clearButton: AppCompatImageView

    private var searchText: String? = null
    private var cursorPosition: Int = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_search)

        searchLine = findViewById(R.id.searchLine)
        clearButton = findViewById(R.id.clearIcon)

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
        }

        searchLine.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clearButton.visibility = if (s.isNullOrEmpty()) View.GONE else View.VISIBLE
                searchText = s?.toString()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        backButton.setOnClickListener {
            finish()
        }

        //сохранение состояния при создании активити
        if (savedInstanceState != null) {
            searchText = savedInstanceState.getString("SEARCH_TEXT")
            cursorPosition = savedInstanceState.getInt("CURSOR_POSITION", 0)
            searchLine.setText(searchText)
            searchLine.setSelection(cursorPosition)
        }

    }

    //сохранение состояния
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("SEARCH_TEXT", searchText)
        outState.putInt("CURSOR_POSITION", searchLine.selectionStart)
    }

    // восстановление состояния
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        searchText = savedInstanceState.getString("SEARCH_TEXT")
        cursorPosition = savedInstanceState.getInt("CURSOR_POSITION", 0)
        searchLine.setText(searchText)
        searchLine.setSelection(cursorPosition)
    }

}
