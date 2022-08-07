package com.tc2r1.innovate.pilotfeedback.viewmodels



import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tc2r1.innovate.pilotfeedback.data.FeedbackObject
import java.time.Year
import java.time.temporal.ChronoField
import kotlin.math.abs
import timber.log.Timber

/**
 * A simple [ViewModel] subclass.
 * Responsible for Handling Business Logic
 * Calculations and Formatting.
 */

class FeedbackViewModel(tempObject: FeedbackObject) : ViewModel() {
    private val _viewState: MutableLiveData<FeedbackViewState> = MutableLiveData()
    val viewState: LiveData<FeedbackViewState> = _viewState

    var selectedCategory: String? = null
    var comment: String = ""
    var storeNum: Int? = null

    private val _showConfirmButton = MutableLiveData(false)
    val showConfirmButton: LiveData<Boolean> = _showConfirmButton

    init {
        _viewState.value = FeedbackViewState(
            isPositive = tempObject.isPositiveFeedback,
            numOfStarts = tempObject.numOfStarts
        )
    }

    fun checkConfirmButton() {
        if (selectedCategory != null && storeNum != null) {
            _showConfirmButton.value = true
        }
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