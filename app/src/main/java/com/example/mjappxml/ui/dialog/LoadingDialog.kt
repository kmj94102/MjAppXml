package com.example.mjappxml.ui.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.mjappxml.R

class LoadingDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Dialog(requireContext(), R.style.TransparentDialog).also {
            it.setContentView(R.layout.dialog_loading)
        }
    }

    override fun show(manager: FragmentManager, tag: String?) {
        isCancelable = false
        super.show(manager, tag)
    }
}