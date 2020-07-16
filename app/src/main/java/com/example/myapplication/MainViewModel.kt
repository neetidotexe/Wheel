package com.example.myapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    var progressValue = MutableLiveData<Double>()
    var errorType = MutableLiveData<ErrorType>()

    fun onClickSubmit(progressText: String?) {
        if (progressText.isNullOrEmpty()) {
            errorType.value = ErrorType.EMPTY_INPUT
            return
        }

        val progress = try {
            progressText.toDouble()
        } catch (ex: NumberFormatException) {
            ex.printStackTrace()
            -1.0
        }

        if (progress in 0.0..100.0) {
            errorType.value = ErrorType.NONE
            progressValue.value = progressText.toDouble()
        } else {
            errorType.value = ErrorType.INVALID_INPUT
        }
    }
}

enum class ErrorType {
    INVALID_INPUT,
    EMPTY_INPUT,
    NONE
}