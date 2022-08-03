package com.tc2r1.android.nudennie_white_boilerplate.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.tc2r1.android.nudennie_white_boilerplate.MyApplication
import com.tc2r1.android.nudennie_white_boilerplate.MyApplication.Companion.numOfStars
import com.tc2r1.android.nudennie_white_boilerplate.data.FeedbackObject
import com.tc2r1.android.nudennie_white_boilerplate.databinding.FragmentFeedbackBinding
import com.tc2r1.android.nudennie_white_boilerplate.viewmodels.FeedbackViewState
import com.tc2r1.android.nudennie_white_boilerplate.viewmodels.TempViewModel2
import com.tc2r1.android.nudennie_white_boilerplate.viewmodels.TempViewModelFactory2

/**
 * A simple [Fragment] subclass.
 * Responsible for Binding Data
 */
class FeedbackFragment : Fragment() {

    private lateinit var viewModelFactory: TempViewModelFactory2

    // ViewModel Scoping. usages delegate to save and cache viewModel.
    private val viewModel: TempViewModel2 by viewModels(
        factoryProducer = { viewModelFactory }
    )

    // DataBinding Variable references to views.
    private lateinit var tempObject: FeedbackObject

    // Contains all the views
    private var _binding: FragmentFeedbackBinding? = null

    // This property is only valid between onCreateView and onDestoryView
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeedbackBinding.inflate(inflater, container, false)
        tempObject = FeedbackObject(MyApplication.isPositive, MyApplication.numOfStars)

        viewModelFactory = TempViewModelFactory2(tempObject)
        binding.apply {

        }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewStateObserver = Observer<FeedbackViewState> { viewState ->
            // Update the UI
            binding.apply {

                if (numOfStars > 3) {
                    tvThankYou.text = "Thank you for your feedback. We’re glad you had a great experience."
                    tvLeaveFeedback.text = "Would you like to leave a comment?"
                } else {
                    tvThankYou.text = "How can we do better?"
                    tvLeaveFeedback.text = "We’re sorry you had a bad experience. Please leave a comment below to let us know how we can be better in the future."
                }
            }

        }

        // observe for changes in the viewstate
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Ensure we clear reference to binding so views are cleaned in memory when destroyed
        // prevents memory leaks
        _binding = null
    }


}