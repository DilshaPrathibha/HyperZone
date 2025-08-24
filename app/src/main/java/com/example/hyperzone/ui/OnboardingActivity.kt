package com.example.hyperzone.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.hyperzone.R
import com.example.hyperzone.databinding.ActivityOnboardingBinding
import com.example.hyperzone.ui.onboarding.OnboardingPagerAdapter

class OnboardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var onboardingPagerAdapter: OnboardingPagerAdapter
    private lateinit var dots: Array<View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupOnboarding()
        setupDots()
        setupButtons()
    }

    private fun setupOnboarding() {
        onboardingPagerAdapter = OnboardingPagerAdapter()
        binding.onboardingViewPager.adapter = onboardingPagerAdapter
        
        binding.onboardingViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                updateDots(position)
                updateButtons(position)
            }
        })
    }

    private fun setupDots() {
        dots = Array(3) { index ->
            val dot = View(this)
            val dotSize = resources.getDimensionPixelSize(R.dimen.dot_size)
            val dotMargin = resources.getDimensionPixelSize(R.dimen.dot_margin)
            
            val params = LinearLayout.LayoutParams(dotSize, dotSize)
            params.marginEnd = dotMargin
            
            dot.layoutParams = params
            dot.background = ContextCompat.getDrawable(this, R.drawable.onboarding_dot_unselected)
            
            binding.dotsContainer.addView(dot)
            dot
        }
        updateDots(0)
    }

    private fun setupButtons() {
        binding.btnSkip.setOnClickListener {
            navigateToLogin()
        }

        binding.btnNext.setOnClickListener {
            val currentItem = binding.onboardingViewPager.currentItem
            if (currentItem == 2) {
                // Last page - Get Started
                navigateToLogin()
            } else {
                // Next page
                binding.onboardingViewPager.currentItem = currentItem + 1
            }
        }

        binding.btnPrevious.setOnClickListener {
            val currentItem = binding.onboardingViewPager.currentItem
            if (currentItem > 0) {
                binding.onboardingViewPager.currentItem = currentItem - 1
            }
        }
    }

    private fun updateDots(position: Int) {
        dots.forEachIndexed { index, dot ->
            if (index == position) {
                dot.background = ContextCompat.getDrawable(this, R.drawable.onboarding_dot_selected)
            } else {
                dot.background = ContextCompat.getDrawable(this, R.drawable.onboarding_dot_unselected)
            }
        }
    }

    private fun updateButtons(position: Int) {
        when (position) {
            0 -> {
                binding.btnPrevious.visibility = View.GONE
                binding.btnNext.text = getString(R.string.next)
            }
            1 -> {
                binding.btnPrevious.visibility = View.VISIBLE
                binding.btnNext.text = getString(R.string.next)
            }
            2 -> {
                binding.btnPrevious.visibility = View.VISIBLE
                binding.btnNext.text = getString(R.string.get_started)
            }
        }
    }

    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}