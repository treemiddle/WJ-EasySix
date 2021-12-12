package com.example.sample.ui.info

import androidx.fragment.app.viewModels
import com.example.sample.R
import com.example.sample.base.BaseFragment
import com.example.sample.databinding.FragmentInfoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InfoFragment : BaseFragment<FragmentInfoBinding>(R.layout.fragment_info) {

    private val viewModel by viewModels<InfoViewModel>()

    override fun bindViews() {
        
    }

    override fun initObserving() {
        with(viewModel) {

        }
    }

    override fun initData() {

    }
}