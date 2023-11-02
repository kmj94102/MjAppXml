package com.example.mjappxml

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.mjappxml.common.repeatOnStarted
import com.example.mjappxml.common.toast
import com.example.mjappxml.ui.dialog.LoadingDialog
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

abstract class BaseViewModelFragment<B : ViewDataBinding, VM : BaseViewModel>(
    @LayoutRes private val layoutId: Int
) : Fragment() {

    protected lateinit var binding: B
    abstract val viewModel: VM
    private val loadingDialog = LoadingDialog()

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

        val status = viewModel.status.value
        repeatOnStarted {
            status.message.collectLatest {
                if (it.isEmpty()) return@collectLatest
                runCatching {
                    requireActivity().toast(it)
                    delay(500)
                    status.updateMessage("")
                }
            }
        }

        repeatOnStarted {
            status.isLoading.collectLatest { if (it) showDialog() else dismissDialog() }
        }

        repeatOnStarted {
            status.isNetworkError.collectLatest { updateNetworkErrorState(it) }
        }

        repeatOnStarted {
            status.isFinish.collectLatest { if (it) onBack() }
        }

        return binding.root
    }

    fun onBack() {
        parentFragmentManager.popBackStack()
    }

    private fun showDialog() = runCatching {
        val transition = parentFragmentManager.beginTransaction()
        transition.remove(loadingDialog)
        transition.add(loadingDialog, "loading").commit()
    }

    private fun dismissDialog() = runCatching {
        loadingDialog.dismiss()
    }

    open fun updateNetworkErrorState(value: Boolean) {}
}