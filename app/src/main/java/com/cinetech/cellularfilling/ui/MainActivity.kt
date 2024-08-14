package com.cinetech.cellularfilling.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import com.cinetech.cellularfilling.databinding.ActivityMainBinding
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModels()

    private lateinit var linearSmoothScroller: LinearSmoothScroller

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setStatusBarIconsAndNavigationBarsLight()
        turnOffNightMode()

        val recyclerAdapter = MainRecyclerAdapter()
        initRecyclerView(recyclerAdapter)
        listenState(recyclerAdapter)
        initCreateButton()
        initLinearSmoothScroller()
    }

    private fun listenState(adapter: MainRecyclerAdapter) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { entities ->
                    adapter.items = entities
                    scrollToLastItem(entities.size - 1)
                }
            }
        }
    }

    private fun scrollToLastItem(lastItemPosition: Int) {
        if (lastItemPosition <= 0) return
        binding.recyclerView.post {
            linearSmoothScroller.targetPosition = lastItemPosition
            binding.recyclerView.layoutManager?.startSmoothScroll(linearSmoothScroller)
        }
    }

    private fun setStatusBarIconsAndNavigationBarsLight() {
        WindowCompat.getInsetsController(this.window, this.window.decorView).apply {
            isAppearanceLightStatusBars = false
            isAppearanceLightNavigationBars = false
        }
    }

    private fun turnOffNightMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    private fun initLinearSmoothScroller() {
        linearSmoothScroller = object : LinearSmoothScroller(binding.recyclerView.context) {

            override fun calculateTimeForScrolling(dx: Int): Int {
                if (dx < 1000) {
                    return 120
                }
                return super.calculateTimeForScrolling(dx)
            }

        }
    }

    private fun initRecyclerView(adapter: MainRecyclerAdapter) {
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun initCreateButton() {
        binding.createcelluletButton.setOnClickListener {
            viewModel.addEntityToTube()
        }
    }
}