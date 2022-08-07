package com.tc2r1.innovate.pilotfeedback.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.tc2r1.innovate.pilotfeedback.MyApplication
import com.tc2r1.innovate.pilotfeedback.R
import com.tc2r1.innovate.pilotfeedback.data.RatingObject
import com.tc2r1.innovate.pilotfeedback.databinding.FragmentRatingBinding
import com.tc2r1.innovate.pilotfeedback.viewmodels.RatingViewModel
import com.tc2r1.innovate.pilotfeedback.viewmodels.RatingViewModelFactory
import com.tc2r1.innovate.pilotfeedback.viewmodels.RatingViewState

/**
 * A simple [Fragment] subclass.
 * Responsible for Binding Data
 */
class RatingFragment : Fragment() {

    private lateinit var viewModelFactory: RatingViewModelFactory

    // ViewModel Scoping. usages delegate to save and cache viewModel.
    private val viewModel: RatingViewModel by viewModels(
        factoryProducer = { viewModelFactory }
    )

    // DataBinding Variable references to views.
    private lateinit var tempObject: RatingObject

    // Contains all the views
    private var _binding: FragmentRatingBinding? = null

    // This property is only valid between onCreateView and onDestoryView
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRatingBinding.inflate(inflater, container, false)
        tempObject =
            RatingObject("Taco Bell", "Where Tacos Are Born.")
        viewModelFactory = RatingViewModelFactory(tempObject)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewStateObserver = Observer<RatingViewState> {
            binding.ratingBar.onRatingBarChangeListener = RatingBar.OnRatingBarChangeListener { ratingBar, rating, _ ->
                MyApplication.numOfStars = rating.toInt()
                ratingBar.findNavController().navigate(RatingFragmentDirections.actionRatingFragmentToFeedbackFragment())
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, requireView().findNavController()) ||
            super.onOptionsItemSelected(item)
    }
}