package com.example.sample.ui.info

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.sample.R
import com.example.sample.base.BaseFragment
import com.example.sample.databinding.FragmentInfoBinding
import com.example.sample.ui.MainViewModel
import com.example.sample.ui.model.view.PresentModel
import com.example.sample.utils.makeLog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InfoFragment : BaseFragment<FragmentInfoBinding>(R.layout.fragment_info) {

    private val viewModel by viewModels<InfoViewModel>()
    private val activityViewModel by activityViewModels<MainViewModel>()

    override fun bindViews() {
        binding.vm = viewModel
    }

    override fun initObserving() {
        with(viewModel) {

        }

        with(activityViewModel) {
            labelAorB.observe(viewLifecycleOwner, {
                setPresentModelBinding(it)
            })
        }
    }

    override fun initData() {

    }

    private fun setPresentModelBinding(item: PresentModel) {
        binding.item = item
        binding.executePendingBindings()
    }

}