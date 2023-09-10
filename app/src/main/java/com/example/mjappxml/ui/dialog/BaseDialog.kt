package com.example.mjappxml.ui.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import com.example.mjappxml.R

abstract class BaseDialog<B : ViewDataBinding>(
    @LayoutRes private val layoutId: Int
) : DialogFragment() {

    protected lateinit var binding: B

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DataBindingUtil.inflate(layoutInflater, layoutId, null, false)

        initState()

        return Dialog(requireActivity(), R.style.TransparentDialog).also {
            it.setContentView(binding.root)
        }
    }

    open fun initState() {}

}