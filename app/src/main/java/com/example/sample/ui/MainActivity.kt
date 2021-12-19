package com.example.sample.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.sample.R
import com.example.sample.base.AppNavigator
import com.example.sample.databinding.ActivityMainBinding
import com.example.sample.utils.EventObserver
import com.example.sample.utils.MapLabelClick
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    @Inject lateinit var navigator: AppNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        initObserve()
        navigator.initMain()
    }

    private fun initObserve() {
        with(mainViewModel) {
            mapLabelClick.observe(this@MainActivity, EventObserver {
                navigator.screenTo(it)
            })
        }
    }

    override fun onBackPressed() {
        when (navigator.currentScreen()) {
            1 -> navigator.screenTo(MapLabelClick.EMPTY)
            2 -> {
                mainViewModel.reset()
                navigator.screenTo(MapLabelClick.RESET)
            }
            else -> super.onBackPressed()
        }
    }
}