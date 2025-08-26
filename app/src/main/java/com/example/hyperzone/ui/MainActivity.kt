package com.example.hyperzone.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.example.hyperzone.ui.compare.CompareActivity
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.commit
import com.example.hyperzone.R
import com.example.hyperzone.cart.CartManager
import com.example.hyperzone.databinding.ActivityMainBinding
import com.example.hyperzone.ui.fragments.CartFragment
import com.example.hyperzone.ui.fragments.CategoriesFragment
import com.example.hyperzone.ui.fragments.ProfileFragment
import com.example.hyperzone.ui.home.HomeFragment
import com.example.hyperzone.ui.notifications.NotificationsActivity
import com.example.hyperzone.ui.orders.OrdersActivity
import com.example.hyperzone.ui.wishlist.WishlistActivity
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var cartBadge: BadgeDrawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar + drawer
        setSupportActionBar(binding.toolbar)
        val toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, binding.toolbar,
            R.string.nav_open, R.string.nav_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        binding.navView.setNavigationItemSelectedListener(this)

        // Default fragment
        if (savedInstanceState == null) {
            supportFragmentManager.commit { replace(R.id.fragment_container, HomeFragment()) }
        }

        // Bottom navigation
        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    supportFragmentManager.commit { replace(R.id.fragment_container, HomeFragment()) }
                    true
                }
                R.id.nav_compare -> {
                    startActivity(Intent(this, CompareActivity::class.java))
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

        // Cart badge observes LiveData (no .count() calls)
        cartBadge = binding.bottomNav.getOrCreateBadge(R.id.nav_cart).apply { isVisible = false }
        CartManager.countLiveData.observe(this) { count ->
            val n = count ?: 0
            if (n > 0) {
                cartBadge.number = n
                cartBadge.isVisible = true
            } else {
                cartBadge.clearNumber()
                cartBadge.isVisible = false
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.drawer_orders -> startActivity(Intent(this, OrdersActivity::class.java))
            R.id.drawer_wishlist -> startActivity(Intent(this, WishlistActivity::class.java))
            R.id.drawer_notifications -> startActivity(Intent(this, NotificationsActivity::class.java))
            R.id.drawer_settings -> startActivity(Intent(this, SettingsActivity::class.java))
            R.id.drawer_help -> { /* TODO: Handle help */ }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
