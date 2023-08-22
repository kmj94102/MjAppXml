package com.example.mjappxml

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.example.mjappxml.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        navController = findNavController(R.id.navHostFragment)
        binding.bottomNavigation.setActivity(this)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bottomNavigation.isVisible = destination.label?.isNotEmpty() == true
        }
    }

    fun goToHome() {
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.navigation_home, true)
            .build()

        navController.navigate(R.id.navigation_home, null, navOptions)
    }

    fun goToGame() {
        navController.navigate(R.id.navigation_game)
    }

    fun goToSchedule() {
        navController.navigate(R.id.navigation_schedule)
    }

    fun goToAccountBook() {
        navController.navigate(R.id.navigation_account_book)
    }

    fun goToOther() {
        navController.navigate(R.id.navigation_other)
    }
}