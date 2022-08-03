package com.tc2r1.android.nudennie_white_boilerplate.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tc2r1.android.nudennie_white_boilerplate.data.TempObject

class TempViewModelFactory(private val tempObject: TempObject) : ViewModelProvider.Factory {
    @Throws(IllegalArgumentException::class)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TempViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TempViewModel(tempObject) as T
        }
        throw IllegalArgumentException("ViewModel Class Passed Is Not Required Type")
    }
}