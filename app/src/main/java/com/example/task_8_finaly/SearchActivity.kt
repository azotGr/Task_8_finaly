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
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SearchActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_search)

        val backButton: ImageButton = findViewById(R.id.buttonBackSearch)
        backButton.setOnClickListener {
            finish()
        }

        val searchLine: EditText = findViewById(R.id.searchLine)
        val clearButton: ImageView = findViewById(R.id.clearIcon)

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
            }

            override fun afterTextChanged(s: Editable?) {}
        })

    }
}