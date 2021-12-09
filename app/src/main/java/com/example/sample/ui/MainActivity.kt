package com.example.sample.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.sample.R
import com.example.sample.base.FragmentFactoryImpl
import com.example.sample.databinding.ActivityMainBinding
import com.example.sample.ui.book.BookFragment
import com.example.sample.ui.history.HistoryFragment
import com.example.sample.ui.info.InfoFragment
import com.example.sample.ui.map.MapFragment
import com.example.sample.utils.EventObserver
import com.example.sample.utils.MapLabelClick
import com.example.sample.utils.ext.addFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = FragmentFactoryImpl()
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        addFragment(R.id.container, MapFragment(), true)
        initObserve()
    }

    private fun initObserve() {
        with(mainViewModel) {
            mapLabelClick.observe(this@MainActivity, EventObserver {
                when (it) {
                    MapLabelClick.LABEL_A -> { addFragment(R.id.container, InfoFragment(), true) }
                    MapLabelClick.LABEL_B -> { addFragment(R.id.container, HistoryFragment(), true) }
                    MapLabelClick.BOOK -> { addFragment(R.id.container, BookFragment(), true) }
                }
            })
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            val dialog = AlertDialog.Builder(this)
            dialog.setTitle("종료하시겠습니까?")
                .setPositiveButton("예") { _, _ -> this.finish() }
                .setNegativeButton("아니요") { _, _ ->  }
            dialog.create()
            dialog.show()
        } else {
            super.onBackPressed()
        }
    }
}