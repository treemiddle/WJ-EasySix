package com.example.sample.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.sample.R
import com.example.sample.base.FragmentFactoryImpl
import com.example.sample.databinding.ActivityMainBinding
import com.example.sample.ui.book.BookFragment
import com.example.sample.ui.history.HistoryFragment
import com.example.sample.ui.info.InfoFragment
import com.example.sample.ui.map.MapFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mapFragment: Fragment
    private lateinit var historyFragment: Fragment
    private lateinit var infoFragment: Fragment
    private lateinit var bookFragment: Fragment
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = FragmentFactoryImpl()
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        initObserve()
        addFragments()
        initFragment()
    }

    private fun initObserve() {
        with(mainViewModel) {

        }
    }

    private fun addFragments() {
        mapFragment = supportFragmentManager.fragmentFactory.instantiate(classLoader, MapFragment::class.java.simpleName)
        historyFragment = supportFragmentManager.fragmentFactory.instantiate(classLoader, HistoryFragment::class.java.simpleName)
        infoFragment = supportFragmentManager.fragmentFactory.instantiate(classLoader, InfoFragment::class.java.simpleName)
        bookFragment = supportFragmentManager.fragmentFactory.instantiate(classLoader, BookFragment::class.java.simpleName)
    }

    private fun initFragment() = supportFragmentManager
        .beginTransaction().run {
            add(R.id.fl_container, mapFragment)
            add(R.id.fl_container, historyFragment)
            add(R.id.fl_container, infoFragment)
            add(R.id.fl_container, bookFragment)
            hide(historyFragment)
            hide(infoFragment)
            hide(bookFragment)
            show(mapFragment)
                .commitNow()
        }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().run {
            when (fragment) {
                is MapFragment -> {
                    hide(historyFragment)
                    hide(infoFragment)
                    hide(bookFragment)
                    show(mapFragment)
                }
                is HistoryFragment -> {
                    hide(mapFragment)
                    hide(infoFragment)
                    hide(bookFragment)
                    show(historyFragment)
                }
                is InfoFragment -> {
                    hide(historyFragment)
                    hide(mapFragment)
                    hide(bookFragment)
                    show(infoFragment)
                }
                is BookFragment -> {
                    hide(historyFragment)
                    hide(infoFragment)
                    hide(mapFragment)
                    show(bookFragment)
                }
                else -> throw IllegalStateException("can't find fragment replaceFragment ${javaClass.simpleName}")
            }
            commitNow()
        }
    }

}