package com.korett.kashentsev.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.korett.kashentsev.MainGraphDirections
import com.korett.kashentsev.R
import com.korett.kashentsev.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment

        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.topMoviesFragment, R.id.favouriteMoviesFragment))
        binding.tbMain.setupWithNavController(navController, appBarConfiguration)

        binding.btPopular.setOnClickListener {
            val action = MainGraphDirections.actionGlobalTopMoviesFragment()
            navController.navigate(
                action
            )
        }

        binding.btFavourite.setOnClickListener {
            val action = MainGraphDirections.actionGlobalFavouriteMoviesFragment()
            navController.navigate(
                action
            )
        }
    }
}