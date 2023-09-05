package com.example.mjappxml.ui.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.mjappxml.R
import com.example.mjappxml.common.dialogResize
import com.example.mjappxml.databinding.DialogSelectOneBinding

class SelectOneDialog(
    private val onSelectItem: (Int) -> Unit
) : DialogFragment() {
    private lateinit var binding: DialogSelectOneBinding
    var title = ""
        private set

    var array = arrayOf("")
        private set

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogSelectOneBinding.inflate(layoutInflater)
        binding.dialog = this
        binding.lifecycleOwner = this

        return Dialog(requireActivity(), R.style.TransparentDialog).also {
            it.setContentView(binding.root)
        }
    }

    fun setTitle(title: String) {
        this.title = title
    }

    fun setPickerValue(array: Array<String>) {
        this.array = array
    }

    fun onSelect() {
        onSelectItem(binding.picker.value)
        dismiss()
    }

    override fun onResume() {
        super.onResume()
        requireContext().dialogResize(dialog)
        tag?.let {
            runCatching {
                binding.picker.value = it.toInt()
            }
        }
    }
}

