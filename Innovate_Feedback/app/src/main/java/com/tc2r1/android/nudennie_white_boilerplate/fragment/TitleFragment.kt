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
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.tc2r1.android.nudennie_white_boilerplate.R
import com.tc2r1.android.nudennie_white_boilerplate.R.string
import com.tc2r1.android.nudennie_white_boilerplate.data.RatingObject
import com.tc2r1.android.nudennie_white_boilerplate.databinding.FragmentTitleBinding
import com.tc2r1.android.nudennie_white_boilerplate.viewmodels.RatingViewModel
import com.tc2r1.android.nudennie_white_boilerplate.viewmodels.RatingViewState

/**
 * A simple [Fragment] subclass.
 * Responsible for Binding Data
 */
class TitleFragment : Fragment() {

    // ViewModel Scoping. usages delegate to save and cache viewModel.
    private val viewModel by activityViewModels<RatingViewModel> { defaultViewModelProviderFactory }
    // DataBinding Variable references to views.
    private lateinit var ratingObject: RatingObject

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
        ratingObject =
            RatingObject(getString(string.title), getString(string.subTitle))
        binding.apply {
            ratingBar.setOnClickListener { view: View ->
                view.findNavController()
                    .navigate(TitleFragmentDirections.actionTitleFragmentToAboutFragment())
            }
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewStateObserver = Observer<RatingViewState> { viewState ->
            // Update the UI
            binding.tvTitle.text = viewState.title
            binding.tvSubtitle.text = viewState.subTitle
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

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, requireView().findNavController()) ||
            super.onOptionsItemSelected(item)
    }
}