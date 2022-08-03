package com.tc2r1.android.nudennie_white_boilerplate.fragment

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
import com.tc2r1.android.nudennie_white_boilerplate.MyApplication
import com.tc2r1.android.nudennie_white_boilerplate.R
import com.tc2r1.android.nudennie_white_boilerplate.data.TempObject
import com.tc2r1.android.nudennie_white_boilerplate.databinding.FragmentTitleBinding
import com.tc2r1.android.nudennie_white_boilerplate.viewmodels.TempViewModel
import com.tc2r1.android.nudennie_white_boilerplate.viewmodels.TempViewModelFactory
import com.tc2r1.android.nudennie_white_boilerplate.viewmodels.TempViewState
import java.time.Year

/**
 * A simple [Fragment] subclass.
 * Responsible for Binding Data
 */
class TitleFragment : Fragment() {

    private lateinit var viewModelFactory: TempViewModelFactory

    // ViewModel Scoping. usages delegate to save and cache viewModel.
    private val viewModel: TempViewModel by viewModels(
        factoryProducer = { viewModelFactory }
    )

    // DataBinding Variable references to views.
    private lateinit var tempObject: TempObject

    // Contains all the views
    private var _binding: FragmentTitleBinding? = null

    // This property is only valid between onCreateView and onDestoryView
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTitleBinding.inflate(inflater, container, false)
        tempObject =
            TempObject(getString(R.string.name), Year.parse(getString(R.string.birth_year)))
        viewModelFactory = TempViewModelFactory(tempObject)
        binding.apply {
            //btnAbout.setOnClickListener { view: View ->
            //    view.findNavController()
            //        .navigate(TitleFragmentDirections.actionTitleFragmentToAboutFragment())
            //}
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewStateObserver = Observer<TempViewState> { viewState ->
            // Update the UI
            //binding.tvName.text = viewState.name
            //binding.tvCurrentAge.text = viewState.age
            //binding.tvCompareAgeToAS.text = viewState.ageVsASResult

            binding.ratingBar.
            setOnRatingBarChangeListener( RatingBar.OnRatingBarChangeListener { ratingBar, rating, fromUser ->

                MyApplication.numOfStars = rating.toInt()
                ratingBar.findNavController().navigate(TitleFragmentDirections.actionTitletFragmentToFeedbackFragment())

            })
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