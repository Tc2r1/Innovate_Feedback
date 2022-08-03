package com.tc2r1.android.nudennie_white_boilerplate.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.tc2r1.android.nudennie_white_boilerplate.R
import com.tc2r1.android.nudennie_white_boilerplate.data.FeedbackObject
import com.tc2r1.android.nudennie_white_boilerplate.databinding.FragmentFeedbackCommentsBinding
import com.tc2r1.android.nudennie_white_boilerplate.viewmodels.FeedbackViewModel

/**
 * A simple [Fragment] subclass.
 * Responsible for Binding Data
 */
class FeedbackFragment : Fragment() {

    // ViewModel Scoping. usages delegate to save and cache viewModel.
    private val viewModel by activityViewModels<FeedbackViewModel> { defaultViewModelProviderFactory }

    // DataBinding Variable references to views.
    private lateinit var feedbackObject: FeedbackObject

    // Contains all the views
    private var _binding: FragmentFeedbackCommentsBinding? = null

    // This property is only valid between onCreateView and onDestoryView
    private val binding get() = _binding!!

    companion object {

        fun getInstance(isPositive: Boolean, numberOfStars: Int) =
            FeedbackFragment().apply {
                this.feedbackObject = FeedbackObject(isPositive, numberOfStars)

                arguments = Bundle().apply {
                    putBoolean("is_positive", isPositive)
                    putInt("number_of_stars", numberOfStars)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val isPositive = arguments?.getBoolean("is_positive") ?: false
        val numberOfStars = arguments?.getBoolean("numberOfStars") ?: false

        _binding = FragmentFeedbackCommentsBinding.inflate(inflater, container, false)

        binding.apply {
            tvThankYou.text = if (isPositive) {
                "Thank you for your feedback. We’re glad you had a great experience."
            } else {
                "How can we do better?"
            }
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // val viewStateObserver = Observer<FeedbackViewState> { viewState ->
        //    // Update the UI
        //    binding.tvTitle.text = viewState.title
        //    binding.tvSubtitle.text = viewState.subTitle
        // }

        // observe for changes in the viewstate
        // viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Ensure we clear reference to binding so views are cleaned in memory when destroyed
        // prevents memory leaks
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, requireView().findNavController()) ||
            super.onOptionsItemSelected(item)
    }
}