package com.example.hyperzone.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.commit
import com.example.hyperzone.R
import com.example.hyperzone.databinding.ActivityMainBinding
import com.example.hyperzone.ui.fragments.CartFragment
import com.example.hyperzone.ui.fragments.CategoriesFragment
import com.example.hyperzone.ui.fragments.HomeFragment
import com.example.hyperzone.ui.fragments.ProfileFragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        // If you imported your logo as a VectorDrawable named "logo":
        // binding.toolbar.logo = AppCompatResources.getDrawable(this, R.drawable.logo)

        val toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, binding.toolbar,
            R.string.nav_open, R.string.nav_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        binding.navView.setNavigationItemSelectedListener(this)

        // Default fragment
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(R.id.fragment_container, HomeFragment())
            }
        }

        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    supportFragmentManager.commit { replace(R.id.fragment_container, HomeFragment()) }
                    true
                }
                R.id.nav_categories -> {
                    supportFragmentManager.commit { replace(R.id.fragment_container, CategoriesFragment()) }
                    true
                }
                R.id.nav_cart -> {
                    supportFragmentManager.commit { replace(R.id.fragment_container, CartFragment()) }
                    true
                }
                R.id.nav_profile -> {
                    supportFragmentManager.commit { replace(R.id.fragment_container, ProfileFragment()) }
                    true
                }
                else -> false
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation drawer item selection
        when (item.itemId) {
            R.id.drawer_orders -> { 
                // Navigate to orders fragment or activity
                supportFragmentManager.commit { 
                    replace(R.id.fragment_container, HomeFragment()) 
                }
            }
            R.id.drawer_wishlist -> { 
                // Navigate to wishlist fragment or activity
                supportFragmentManager.commit { 
                    replace(R.id.fragment_container, HomeFragment()) 
                }
            }
            R.id.drawer_notifications -> { 
                // Navigate to notifications fragment or activity
                supportFragmentManager.commit { 
                    replace(R.id.fragment_container, HomeFragment()) 
                }
            }
            R.id.drawer_settings -> { 
                // Navigate to settings activity
                // startActivity(Intent(this, SettingsActivity::class.java))
                supportFragmentManager.commit { 
                    replace(R.id.fragment_container, HomeFragment()) 
                }
            }
            R.id.drawer_help -> { 
                // Navigate to help fragment or activity
                supportFragmentManager.commit { 
                    replace(R.id.fragment_container, HomeFragment()) 
                }
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
