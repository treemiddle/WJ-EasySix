package com.example.sample.ui.history

import com.example.sample.R
import com.example.sample.base.BaseFragment
import com.example.sample.databinding.FragmentHistoryBinding
import com.example.sample.ui.info.InfoFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : BaseFragment<FragmentHistoryBinding>(R.layout.fragment_history) {

    override fun initData() {

    }

    override fun bindViews() {

    }

    override fun initObserving() {

    }

    companion object {
        fun newInstance(): HistoryFragment {
            return HistoryFragment()
        }
    }

}