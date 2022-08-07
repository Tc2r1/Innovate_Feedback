package com.tc2r1.innovate.pilotfeedback.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tc2r1.innovate.pilotfeedback.data.RatingObject

class RatingViewModelFactory(private val tempObject: RatingObject) : ViewModelProvider.Factory {
    @Throws(IllegalArgumentException::class)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RatingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RatingViewModel(tempObject) as T
        }
        throw IllegalArgumentException("ViewModel Class Passed Is Not Required Type")
    }
}