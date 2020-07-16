package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private lateinit var enterProgress : EditText
    private lateinit var animateButton : Button
    private lateinit var circularProgressBar : CircularProgressBar
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        enterProgress = findViewById(R.id.text_progress)
        animateButton = findViewById(R.id.button_animate)
        circularProgressBar = findViewById(R.id.circular_progress_bar)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        animateButton.setOnClickListener {
            val progressString = enterProgress.text.toString()
            viewModel.onClickSubmit(progressString)
        }

        viewModel.progressValue.observe(this, Observer {
            circularProgressBar.setCircularProgressBar(it)
        })

        viewModel.errorType.observe(this, Observer {
            when(it){
                ErrorType.INVALID_INPUT -> showInvalidInputError()
                ErrorType.EMPTY_INPUT -> showEmptyInputError()
            }
        })
    }

    private fun showInvalidInputError(){
        Toast.makeText(this, "Progress can not be more than 100", Toast.LENGTH_LONG).show()
    }

    private fun showEmptyInputError(){
        Toast.makeText(this, "Please enter a progress value", Toast.LENGTH_LONG).show()
    }
}
