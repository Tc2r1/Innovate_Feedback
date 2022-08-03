/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tc2r1.android.nudennie_white_boilerplate

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.LifecycleObserver
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.tc2r1.android.nudennie_white_boilerplate.databinding.ActivityMainBinding
import timber.log.Timber

class MainActivity : AppCompatActivity(), LifecycleObserver {

    // Contains all the views
    private var _binding: ActivityMainBinding? = null

    // This property is only valid between onCreateView and onDestroyView
    private val binding get() = _binding!!

    // Side Sliding Drawer
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onStart() {
        super.onStart()
        Timber.i("onStart Called")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.i("onCreate Called")
        // Use Data Binding to get reference to the views
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        drawerLayout = binding.drawerLayout

        // Setup Navigation Controller
        val navController = this.findNavController(R.id.myNavHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        // Setup Appbar Configuration
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        // Setup NavController Listener
        navController.addOnDestinationChangedListener { controller, destination, _ ->
            when (destination.id) {
                controller.graph.startDestinationId -> drawerLayout.setDrawerLockMode(
                    DrawerLayout.LOCK_MODE_UNLOCKED
                )
                else -> drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }
        NavigationUI.setupWithNavController(binding.navView, navController)

        // Hide Keyboard when complete.
        //        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        //        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onResume() {
        super.onResume()
        Timber.i("onResume Called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i("onDestroy Called")

        // Ensure we clear reference to binding so views are cleaned in memory when destroyed
        // prevents memory leaks
        _binding = null
    }

    /**
     * Menu methods
     */
    private fun onShare() {
        val shareIntent = ShareCompat.IntentBuilder(this)
            .setText(getString(R.string.share_text, "Application Goes Here"))
            .setType("text/plain")
            .intent
        try {
            startActivity(shareIntent)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(
                this, getString(R.string.sharing_not_available),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.shareMenuButton -> onShare()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        return NavigationUI.navigateUp(navController, drawerLayout)
    }
}