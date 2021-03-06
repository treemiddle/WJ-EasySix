package com.example.sample.base

import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import com.example.sample.R
import com.example.sample.ui.book.BookFragment
import com.example.sample.ui.history.HistoryFragment
import com.example.sample.ui.info.InfoFragment
import com.example.sample.ui.map.MapFragment
import com.example.sample.utils.MapLabelClick
import com.example.sample.utils.makeLog
import javax.inject.Inject

class AppNavigatorImpl @Inject constructor(private val activity: FragmentActivity) : AppNavigator {

    private val mapFragment = MapFragment.newInstance()
    private val historyFragment = HistoryFragment.newInstance()
    private val infoFragment = InfoFragment.newInstance()
    private val bookFragment = BookFragment.newInstance()

    override fun initMain() {
        setMainFragment()
    }

    override fun setMain() {
        setMainFragment()
    }

    override fun screenTo(screen: MapLabelClick) {
        replaceFragment(screen)
    }

    private fun setMainFragment() {
        activity.supportFragmentManager.commit {
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

    private fun replaceFragment(screen: MapLabelClick) {
        activity.supportFragmentManager.commit {
            when (screen) {
                MapLabelClick.LABEL_A, MapLabelClick.LABEL_B -> {
                    hide(historyFragment)
                    hide(bookFragment)
                    hide(mapFragment)
                    show(infoFragment)
                }
                MapLabelClick.EMPTY -> {
                    hide(historyFragment)
                    hide(bookFragment)
                    hide(infoFragment)
                    show(mapFragment)
                }
                MapLabelClick.BOOK -> {
                    hide(historyFragment)
                    hide(mapFragment)
                    hide(infoFragment)
                    show(bookFragment)
                }
                MapLabelClick.RESET -> {
                    hide(historyFragment)
                    hide(bookFragment)
                    hide(infoFragment)
                    show(mapFragment)
                }
                MapLabelClick.LAST_SCREEN -> {
                    hide(bookFragment)
                    hide(mapFragment)
                    hide(infoFragment)
                    show(historyFragment)
                }
            }
        }
    }

    override fun currentScreen(): Int {
        return when {
            mapFragment.isVisible && mapFragment.isResumed -> 0
            infoFragment.isVisible && infoFragment.isResumed -> 1
            bookFragment.isVisible && bookFragment.isResumed -> 2
            historyFragment.isVisible && historyFragment.isResumed -> 3
            else -> 0
        }
    }

}