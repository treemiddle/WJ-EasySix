package com.example.sample.ui.info

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.transition.TransitionInflater
import com.example.sample.R
import com.example.sample.base.BaseFragment
import com.example.sample.databinding.FragmentInfoBinding
import com.example.sample.ui.MainViewModel
import com.example.sample.ui.model.view.PresentModel
import com.example.sample.utils.MapLabelClick
import com.example.sample.utils.StackManager
import com.example.sample.utils.makeLog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InfoFragment : BaseFragment<FragmentInfoBinding>(R.layout.fragment_info) {

    private val viewModel by viewModels<InfoViewModel>()
    private val activityViewModel by activityViewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        enterTransition = inflater.inflateTransition(R.transition.slide_right)
    }

    override fun bindViews() {
        binding.vm = viewModel

        activity?.onBackPressedDispatcher?.addCallback(
            this, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    activityViewModel.setBackStack(StackManager.INFO_TO_MAP)
                }
            }
        )
    }

    override fun initObserving() {
        with(viewModel) {
            checkNickName.observe(viewLifecycleOwner, {
                activityViewModel.moveScreen(MapLabelClick.EMPTY)
            })
            updateLabelModel.observe(viewLifecycleOwner, {
                activityViewModel.setInMemoryLabel(it)
            })
        }

        with(activityViewModel) {
            labelAorB.observe(viewLifecycleOwner, {
                viewModel.setLabelModel(it)
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

    companion object {
        fun newInstance(): InfoFragment {
            return InfoFragment()
        }
    }

}