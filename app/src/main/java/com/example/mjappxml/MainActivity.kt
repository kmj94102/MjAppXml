package com.example.mjappxml

import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowInsets
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.isVisible
import androidx.navigation.NavController
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

//        setStatusBarColor(this.window, ContextCompat.getColor(this, R.color.black))
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

    fun goToPage(resId: Int, bundle: Bundle?= null) {
        navController.navigate(resId, bundle)
    }

    fun setStatusBarColor(window: Window, color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) { // Android 15+
            window.decorView.setOnApplyWindowInsetsListener { view, insets ->
                val statusBarInsets = insets.getInsets(WindowInsets.Type.statusBars())
                view.setBackgroundColor(color)

                // Adjust padding to avoid overlap
                view.setPadding(0, statusBarInsets.top, 0, 0)
                insets
            }
        } else {
            // For Android 14 and below
            window.statusBarColor = color
        }
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = false
    }
}