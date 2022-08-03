package com.tc2r1.android.nudennie_white_boilerplate.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tc2r1.android.nudennie_white_boilerplate.data.FeedbackObject

class TempViewModelFactory2(private val tempObject: FeedbackObject) : ViewModelProvider.Factory {
    @Throws(IllegalArgumentException::class)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TempViewModel2::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TempViewModel2(tempObject) as T
        }
        throw IllegalArgumentException("ViewModel Class Passed Is Not Required Type")
    }
}