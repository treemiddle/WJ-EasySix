package com.example.sample.ui.book

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.sample.R
import com.example.sample.base.BaseFragment
import com.example.sample.databinding.FragmentBookBinding
import com.example.sample.ui.MainViewModel
import com.example.sample.ui.info.InfoFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookFragment : BaseFragment<FragmentBookBinding>(R.layout.fragment_book) {

    private val viewModel by viewModels<BookViewModel>()
    private val activityViewModel by activityViewModels<MainViewModel>()

    override fun bindViews() {
        binding.vm = viewModel
    }

    override fun initObserving() {
        with(viewModel) {

        }

        with(activityViewModel) {

        }
    }

    override fun initData() {
        viewModel.test()
    }

    companion object {
        fun newInstance(): BookFragment {
            return BookFragment()
        }
    }

}