package com.tc2r1.android.nudennie_white_boilerplate.viewmodels



import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tc2r1.android.nudennie_white_boilerplate.data.RatingObject

/**
 * A simple [ViewModel] subclass.
 * Responsible for Handling Business Logic
 * Calculations and Formatting.
 */

class RatingViewModel(ratingInfo: RatingObject) : ViewModel() {
    private val _viewState: MutableLiveData<RatingViewState> = MutableLiveData()
    val viewState: LiveData<RatingViewState> = _viewState

    init {
        _viewState.value = RatingViewState(
            title = ratingInfo.title,
            subTitle = ratingInfo.subTitle,
        )
    }
}