/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tc2r1.android.nudennie_white_boilerplate.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tc2r1.android.nudennie_white_boilerplate.R
import com.tc2r1.android.nudennie_white_boilerplate.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {

    private lateinit var aboutDescription: String

    // Contains all the views
    private var _binding: FragmentAboutBinding? = null

    // This property is only valid between onCreateView and onDestoryView
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        // binding.tvAbout.text = getString(R.string.about_text)
        aboutDescription = getString(R.string.about_text)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Ensure we clear reference to binding so views are cleaned in memory when destroyed
        // prevents memory leaks
        _binding = null
    }
}