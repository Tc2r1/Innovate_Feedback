package com.tc2r1.android.nudennie_white_boilerplate.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tc2r1.android.nudennie_white_boilerplate.data.FeedbackObject
import java.time.Year
import java.time.temporal.ChronoField
import kotlin.math.abs
import timber.log.Timber


/**
 * A simple [ViewModel] subclass.
 * Responsible for Handling Business Logic
 * Calculations and Formatting.
 */

class TempViewModel2(tempObject: FeedbackObject) : ViewModel() {
    private val _viewState: MutableLiveData<FeedbackViewState> = MutableLiveData()
    val viewState: LiveData<FeedbackViewState> = _viewState

    init {
        _viewState.value = FeedbackViewState(
            isPositive = tempObject.isPositiveFeedback,
            numOfStarts = tempObject.numOfStarts,
        )
    }

    // Does not account for Leap Years, I could add it but this is
    // suppose to be boiler plate code and I've already gotten carried away.
    private fun formatCurrentAgeString(years: Int): String {
        return when {
            years > 0 -> {
                "This person is $years old!"
            }
            years < 0 -> {
                val posYear = abs(years)
                "$years This person is from the future, They will be born in $posYear. "
            }
            else -> {
                "This person is less than a year old!"
            }
        }
    }

    private fun formatAndroidStudioResult(years: Int): String {
        return when {
            years > 0 -> {
                "$years years OLDER than Android Studio!"
            }
            years < 0 -> {
                "$years years YOUNGER than Android Studio!"
            }
            else -> {
                "Same Age As Android Studio!"
            }
        }
    }

    private fun calculateDifferenceInYears(
        oldestYear: Year,
        mostCurrentYear: Year = Year.now()
    ): Int {
        val differenceInYears =
            mostCurrentYear.minusYears(oldestYear.getLong(ChronoField.YEAR_OF_ERA)).value
        Timber.i(differenceInYears.toString())
        return differenceInYears
    }
}