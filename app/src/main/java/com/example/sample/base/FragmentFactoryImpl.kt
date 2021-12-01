package com.example.sample.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.example.sample.ui.book.BookFragment
import com.example.sample.ui.history.HistoryFragment
import com.example.sample.ui.info.InfoFragment
import com.example.sample.ui.map.MapFragment

class FragmentFactoryImpl : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            BookFragment::class.java.simpleName -> { BookFragment() }
            HistoryFragment::class.java.simpleName -> { HistoryFragment() }
            InfoFragment::class.java.simpleName -> { InfoFragment() }
            MapFragment::class.java.simpleName -> { MapFragment() }
            else -> super.instantiate(classLoader, className)
        }
    }

}