package com.example.sample.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.sample.R
import com.example.sample.base.BackPressHandler
import com.example.sample.base.FragmentFactoryImpl
import com.example.sample.databinding.ActivityMainBinding
import com.example.sample.ui.book.BookFragment
import com.example.sample.ui.history.HistoryFragment
import com.example.sample.ui.info.InfoFragment
import com.example.sample.ui.map.MapFragment
import com.example.sample.utils.EventObserver
import com.example.sample.utils.MapLabelClick
import com.example.sample.utils.ext.getTopFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlin.system.exitProcess

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    private lateinit var mapFragment: Fragment
    private lateinit var historyFragment: Fragment
    private lateinit var infoFragment: Fragment
    private lateinit var bookFragment: Fragment

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
            mapLabelClick.observe(this@MainActivity, EventObserver {
                when (it) {
                    MapLabelClick.LABEL_A, MapLabelClick.LABEL_B -> replaceFragment(infoFragment)
                    MapLabelClick.EMPTY -> replaceFragment(mapFragment)
                    else -> {
                    }
                }
            })
        }
    }

    private fun addFragments() {
        mapFragment = supportFragmentManager.fragmentFactory.instantiate(classLoader, MapFragment::class.java.simpleName)
        historyFragment = supportFragmentManager.fragmentFactory.instantiate(classLoader, HistoryFragment::class.java.simpleName)
        infoFragment = supportFragmentManager.fragmentFactory.instantiate(classLoader, InfoFragment::class.java.simpleName)
        bookFragment = supportFragmentManager.fragmentFactory.instantiate(classLoader, BookFragment::class.java.simpleName)
    }

    private fun initFragment() {
        supportFragmentManager.commit {
            add(R.id.container, mapFragment)
            add(R.id.container, historyFragment)
            add(R.id.container, infoFragment)
            add(R.id.container, bookFragment)
            hide(historyFragment)
            hide(infoFragment)
            hide(bookFragment)
            show(mapFragment)
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            when (fragment) {
                is MapFragment -> {
                    hide(historyFragment)
                    hide(bookFragment)
                    hide(infoFragment)
                    show(mapFragment)
                }
                is BookFragment -> {
                    hide(historyFragment)
                    hide(infoFragment)
                    hide(mapFragment)
                    show(bookFragment)
                }
                is InfoFragment -> {
                    hide(historyFragment)
                    hide(bookFragment)
                    hide(mapFragment)
                    setCustomAnimations(
                        R.anim.slide_in,
                        R.anim.fade_out,
                        R.anim.fade_in,
                        R.anim.slide_out
                    )
                    show(infoFragment)
                }
                is HistoryFragment -> {
                    hide(bookFragment)
                    hide(infoFragment)
                    hide(mapFragment)
                    show(historyFragment)
                }
            }
        }
    }

    private fun isTopFragmentConsumeBackPress() = getTopFragment<BackPressHandler>()?.onBackPressed() == true

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            val dialog = AlertDialog.Builder(this)
            dialog.setTitle("종료하시겠습니까?")
                .setPositiveButton("예") { _, _ -> this.finish() }
                .setNegativeButton("아니요") { _, _ -> }
            dialog.create()
            dialog.show()
        } else {
            super.onBackPressed()
        }
//        if (!isTopFragmentConsumeBackPress()) {
//            super.onBackPressed()
//        }
    }
}