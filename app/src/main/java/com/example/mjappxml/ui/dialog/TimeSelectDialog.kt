package com.example.mjappxml.ui.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.mjappxml.R
import com.example.mjappxml.common.dialogResize
import com.example.mjappxml.databinding.DialogTimeSelectBinding

class TimeSelectDialog(
    private val onSelectItem: (String) -> Unit
) : DialogFragment() {
    private lateinit var binding: DialogTimeSelectBinding
    var title = ""
        private set

    val hourArray = (0..23).map { "$it".padStart(2, '0') }.toTypedArray()

    val minuteArray = (0..59).map { "$it".padStart(2, '0') }.toTypedArray()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogTimeSelectBinding.inflate(layoutInflater)
        binding.dialog = this
        binding.lifecycleOwner = this

        return Dialog(requireActivity(), R.style.TransparentDialog).also {
            it.setContentView(binding.root)
        }
    }

    fun setTitle(title: String = "시간 선택") {
        this.title = title
    }

    fun onSelect() {
        onSelectItem("${hourArray[binding.hourPicker.value]}:${minuteArray[binding.minutePicker.value]}")
        dismiss()
    }

    override fun onResume() {
        super.onResume()
        requireContext().dialogResize(dialog)
        tag?.let {
            runCatching {
                val hour = it.substring(0, 2)
                val minute = it.substring(3, 5)

                binding.hourPicker.value = hourArray.indexOf(hour)
                binding.minutePicker.value = minuteArray.indexOf(minute)
            }
        }
    }
}

