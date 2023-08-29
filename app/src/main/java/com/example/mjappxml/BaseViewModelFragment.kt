package com.example.mjappxml

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel

abstract class BaseViewModelFragment<B : ViewDataBinding, VM : ViewModel>(
    @LayoutRes private val layoutId : Int
) : Fragment() {

    protected lateinit var binding: B
    abstract val viewModel : VM
//    protected lateinit var dialog : LoadingDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(layoutInflater, layoutId, container, false)

        with(binding) {
            lifecycleOwner = this@BaseViewModelFragment
            setVariable(BR.homeViewModel, viewModel)
        }

        return binding.root
    }

    fun onBack() {
        parentFragmentManager.popBackStack()
    }

    protected fun showDialog() {
//        dialog = LoadingDialog().also {
//            it.show(parentFragmentManager, null)
//        }
    }

    protected fun dismissDialog(){
//        if (this::dialog.isInitialized) {
//            dialog.dismissDialog()
//        }
    }
}