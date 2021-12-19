package com.example.sample.ui.history

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.sample.R
import com.example.sample.base.BaseFragment
import com.example.sample.databinding.FragmentHistoryBinding
import com.example.sample.ui.MainViewModel
import com.example.sample.ui.book.BookAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HistoryFragment : BaseFragment<FragmentHistoryBinding>(R.layout.fragment_history) {

    private val activityViewModel by activityViewModels<MainViewModel>()
    private val viewModel by viewModels<HistoryViewModel>()
    @Inject lateinit var adapter: BookAdapter

    override fun bindViews() {
        binding.vm = viewModel
    }

    override fun initObserving() {
        with(viewModel) {
            labelList.observe(viewLifecycleOwner, {
                adapter.submitList(it)
            })
        }

        with(activityViewModel) {
            history.observe(viewLifecycleOwner, {
                viewModel.getHistory()
            })
        }
    }

    override fun initData() {
        initAdapter()
    }

    private fun initAdapter() {
        binding.rv.adapter = adapter
    }

    companion object {
        fun newInstance(): HistoryFragment {
            return HistoryFragment()
        }
    }

}