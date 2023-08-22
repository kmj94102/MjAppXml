package com.example.mjappxml

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel

abstract class BaseViewModelActivity<B : ViewDataBinding, VM : ViewModel>(
    @LayoutRes private val layoutId : Int
) : BaseActivity<B>(layoutId) {

    abstract val viewModel : VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)

        with(binding) {
            lifecycleOwner = this@BaseViewModelActivity
            setVariable(BR._all, viewModel)
        }
    }

}