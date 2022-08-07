package com.tc2r1.innovate.pilotfeedback.fragment

import android.R.layout
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.tc2r1.innovate.pilotfeedback.MyApplication
import com.tc2r1.innovate.pilotfeedback.MyApplication.Companion.numOfStars
import com.tc2r1.innovate.pilotfeedback.R.string
import com.tc2r1.innovate.pilotfeedback.data.FeedbackObject
import com.tc2r1.innovate.pilotfeedback.databinding.FragmentFeedbackBinding
import com.tc2r1.innovate.pilotfeedback.repository.FeedBackResponse
import com.tc2r1.innovate.pilotfeedback.repository.FeedbackRequestBody
import com.tc2r1.innovate.pilotfeedback.repository.RestAPI
import com.tc2r1.innovate.pilotfeedback.viewmodels.FeedbackViewModel
import com.tc2r1.innovate.pilotfeedback.viewmodels.FeedbackViewModelFactory
import com.tc2r1.innovate.pilotfeedback.viewmodels.FeedbackViewState
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory

/**
 * A simple [Fragment] subclass.
 * Responsible for Binding Data
 */
class FeedbackFragment : Fragment(), AdapterView.OnItemSelectedListener {

    companion object {
        var categories = arrayOf("Food", "Facility", "Shower", "Staff", "Mobile App", "Other")
        const val CAT_SPINNER_ID = 1
    }
    private lateinit var viewModelFactory: FeedbackViewModelFactory

    // ViewModel Scoping. usages delegate to save and cache viewModel.
    private val viewModel: FeedbackViewModel by viewModels(
        factoryProducer = { viewModelFactory }
    )

    // DataBinding Variable references to views.
    private lateinit var tempObject: FeedbackObject

    // Contains all the views
    private var _binding: FragmentFeedbackBinding? = null

    // This property is only valid between onCreateView and onDestoryView
    private val binding get() = _binding!!

    private val client = OkHttpClient.Builder().build()

    private var retrofit = Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://wgpeybfcel.execute-api.us-east-1.amazonaws.com/api/v1/")
        .client(client)
        .build()

    private var restAPI: RestAPI = retrofit.create(RestAPI::class.java)

    private fun submitFeedback(feedObject: FeedbackRequestBody) {
        val dataReturned = restAPI.submitGuestFeedback(feedObject)
        dataReturned.enqueue(
            object : Callback<FeedBackResponse> {
                override fun onFailure(call: Call<FeedBackResponse>, t: Throwable) {
                    onResult(null)
                }

                override fun onResponse(call: Call<FeedBackResponse>, response: Response<FeedBackResponse>) {
                    val feedBackInfo = response.body()
                    onResult(feedBackInfo)
                }
            }
        )
    }

    private fun onResult(response: FeedBackResponse?) {
        Log.wtf("Test", "Response is $response")

        Toast.makeText(requireContext(), "Feedback Submitted!", Toast.LENGTH_LONG).show()
        this.findNavController().navigate(FeedbackFragmentDirections.actionFeedbackFragmentToHomeFragment())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeedbackBinding.inflate(inflater, container, false)
        tempObject = FeedbackObject(MyApplication.isPositive, numOfStars)

        val aa = ArrayAdapter(context!!, layout.simple_spinner_dropdown_item, categories)
        with(binding.spinner) {
            adapter = aa
            setSelection(0, false)
            onItemSelectedListener = this@FeedbackFragment
            prompt = if (numOfStars > 3) context.getString(string.spinner_prompt_positive) else context.getString(string.spinner_prompt_negative)
            gravity = Gravity.CENTER
        }

        viewModelFactory = FeedbackViewModelFactory(tempObject)
        binding.spinner.onItemSelectedListener = this

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewStateObserver = Observer<FeedbackViewState> {
            // Update the UI
            binding.apply {
                if (numOfStars > 3) {
                    tvThankYou.text = getString(string.confirm_feedback_positive)
                    tvLeaveFeedback.text = getString(string.subtitle_feedback_positive)
                } else {
                    tvThankYou.text = getString(string.title_feedback_negative)
                    tvLeaveFeedback.text = getString(string.subtitle_feedback_negative)
                }

                btnSubmit.setOnClickListener {
                    submitFeedback(
                        feedObject = FeedbackRequestBody(
                            feedback_category = if (viewModel.selectedCategory != null) viewModel.selectedCategory!! else "Other",
                            feedback_rating = numOfStars,
                            feedback_text = viewModel.comment,
                            loyalty_id = 1234,
                            store_id = viewModel.storeNum!!
                        )
                    )
                }

                etStoreNumber.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    }

                    override fun afterTextChanged(s: Editable?) {
                        if (s.isNullOrBlank()) return

                        viewModel.storeNum = s.toString().toInt()
                        viewModel.checkConfirmButton()
                    }
                })

                etLeaveComment.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    }

                    override fun afterTextChanged(s: Editable?) {
                        viewModel.comment = s.toString()
                        viewModel.checkConfirmButton()
                    }
                })
            }
        }

        // observe for changes in the viewstate
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver)

        // observe for changes in confirm Button State
        viewModel.showConfirmButton.observe(
            viewLifecycleOwner
        ) { shouldShowButton ->
            binding.btnSubmit.isEnabled = shouldShowButton
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Ensure we clear reference to binding so views are cleaned in memory when destroyed
        // prevents memory leaks
        _binding = null
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        viewModel.selectedCategory = categories[position]
        Toast.makeText(
            context,
            viewModel.selectedCategory,
            Toast.LENGTH_LONG
        ).show()
        viewModel.checkConfirmButton()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }
}