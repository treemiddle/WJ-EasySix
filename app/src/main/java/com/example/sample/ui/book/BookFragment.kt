package com.example.sample.ui.book

import com.example.sample.R
import com.example.sample.base.BaseFragment
import com.example.sample.databinding.FragmentBookBinding
import com.example.sample.ui.info.InfoFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookFragment : BaseFragment<FragmentBookBinding>(R.layout.fragment_book) {

    override fun initData() {

    }

    override fun bindViews() {

    }

    override fun initObserving() {

    }

    companion object {
        fun newInstance(): BookFragment {
            return BookFragment()
        }
    }

}