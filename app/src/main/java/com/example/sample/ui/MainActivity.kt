package com.example.sample.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.sample.R
import com.example.sample.base.AppNavigator
import com.example.sample.databinding.ActivityMainBinding
import com.example.sample.utils.EventObserver
import com.example.sample.utils.MapLabelClick
import com.example.sample.utils.StackManager
import com.example.sample.utils.makeLog
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
        when (mainViewModel.getBackStack()) {
            StackManager.MAP_TO_MAP -> {
                super.onBackPressed()
            }
            StackManager.INFO_TO_MAP -> {
                navigator.screenTo(MapLabelClick.EMPTY)
            }
            StackManager.BOOK_TO_MAP -> {
                navigator.screenTo(MapLabelClick.EMPTY)
            }
            StackManager.NORMAL -> {
                super.onBackPressed()
            }
        }
    }
}