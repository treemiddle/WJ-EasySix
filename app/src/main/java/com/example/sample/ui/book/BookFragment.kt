package com.example.sample.ui.book

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.sample.R
import com.example.sample.base.BaseFragment
import com.example.sample.databinding.FragmentBookBinding
import com.example.sample.ui.MainViewModel
import com.example.sample.utils.MapLabelClick
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BookFragment : BaseFragment<FragmentBookBinding>(R.layout.fragment_book) {

    private val viewModel by viewModels<BookViewModel>()
    private val activityViewModel by activityViewModels<MainViewModel>()
    @Inject lateinit var adapter: BookAdapter

    override fun bindViews() {
        binding.vm = viewModel
    }

    override fun initObserving() {
        with(viewModel) {
            mockList.observe(viewLifecycleOwner, {
                adapter.submitList(it)
            })
            next.observe(viewLifecycleOwner, {
                activityViewModel.openHistory()
                activityViewModel.moveScreen(MapLabelClick.LAST_SCREEN)
            })
        }

        with(activityViewModel) {
            labelSet.observe(viewLifecycleOwner, {
                viewModel.getBooks(it)
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
        fun newInstance(): BookFragment {
            return BookFragment()
        }
    }

}