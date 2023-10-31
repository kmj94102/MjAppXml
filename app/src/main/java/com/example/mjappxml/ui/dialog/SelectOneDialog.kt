package com.example.mjappxml.ui.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.annotation.ColorInt
import androidx.fragment.app.DialogFragment
import com.example.mjappxml.R
import com.example.mjappxml.common.dialogResize
import com.example.mjappxml.databinding.DialogSelectOneBinding
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SelectOneDialog(
    private val onSelectItem: (Int) -> Unit
) : DialogFragment() {
    private lateinit var binding: DialogSelectOneBinding
    private val _primaryColor = MutableStateFlow(R.color.red)
    val primaryColor: StateFlow<Int> = _primaryColor

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

    fun setPrimaryColor(@ColorInt color: Int) {
        _primaryColor.value = color
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

