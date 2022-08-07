package com.tc2r1.innovate.pilotfeedback.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tc2r1.innovate.pilotfeedback.data.FeedbackObject

class FeedbackViewModelFactory(private val tempObject: FeedbackObject) : ViewModelProvider.Factory {
    @Throws(IllegalArgumentException::class)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FeedbackViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FeedbackViewModel(tempObject) as T
        }
        throw IllegalArgumentException("ViewModel Class Passed Is Not Required Type")
    }
}