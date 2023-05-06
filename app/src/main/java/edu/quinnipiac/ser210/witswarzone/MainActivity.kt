/*
    App:   WITSWARZONE
    Names: Emilio Cruz, William Siri
    Date: May 2023
 */

package edu.quinnipiac.ser210.witswarzone

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import edu.quinnipiac.ser210.witswarzone.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity()
{
    private lateinit var _binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        // init toolbar
        setSupportActionBar(_binding.toolbar)
        supportActionBar?.title = ""

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val builder = AppBarConfiguration.Builder(navController.graph)
        val appBarConfiguration = builder.build()

        // set up navigation with toolbar
        _binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        _binding.bottomNav.setupWithNavController(navController)
    }

    // navigates to specified fragment from menu option
    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        val navController = findNavController(R.id.nav_host_fragment)

        return super.onOptionsItemSelected(item) || item.onNavDestinationSelected(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}