package com.example.sample.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable

abstract class BaseFragment<VDB : ViewDataBinding>(
    @LayoutRes
    private val layoutResId: Int
) : Fragment() {

    protected lateinit var binding: VDB
    protected val compositeDisposable by lazy(::CompositeDisposable)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this

        bindViews()
        initData()
    }

    abstract fun initData()
    abstract fun bindViews()

    override fun onDestroyView() {
        compositeDisposable.clear()
        super.onDestroyView()
    }



}