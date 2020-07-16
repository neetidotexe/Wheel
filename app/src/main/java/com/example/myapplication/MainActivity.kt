package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    private lateinit var enterProgress : EditText
    private lateinit var animateButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        enterProgress = findViewById(R.id.text_progress)
        animateButton = findViewById(R.id.button_animate)

        animateButton.setOnClickListener {

        }
    }
}
