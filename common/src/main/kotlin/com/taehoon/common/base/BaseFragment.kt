package com.taehoon.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.taehoon.common.mvi.MviSideEffect
import com.taehoon.common.mvi.MviViewState

abstract class BaseFragment<VIEW_BINDING : ViewBinding, VIEW_STATE : MviViewState, SIDE_EFFECT : MviSideEffect> : Fragment() {
    private var _binding: VIEW_BINDING? = null
    protected val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = generateViewBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        afterInitView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    abstract fun generateViewBinding(): VIEW_BINDING
    abstract fun initView()
    abstract fun afterInitView()
    abstract fun render(viewState: VIEW_STATE)
    abstract fun handleSideEffect(sideEffect: SIDE_EFFECT)
}