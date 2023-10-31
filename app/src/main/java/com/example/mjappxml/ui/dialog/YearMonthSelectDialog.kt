package com.example.mjappxml.ui.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.annotation.ColorInt
import androidx.fragment.app.DialogFragment
import com.example.mjappxml.R
import com.example.mjappxml.common.dialogResize
import com.example.mjappxml.databinding.DialogYearMonthSelectBinding
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class YearMonthSelectDialog(
    private val onSelectItem: (String, String) -> Unit
) : DialogFragment() {
    private lateinit var binding: DialogYearMonthSelectBinding
    private val _primaryColor = MutableStateFlow(R.color.red)
    val primaryColor: StateFlow<Int> = _primaryColor

    var title = ""
        private set

    var yearArray = (2020..2040).map { "$it" }.toTypedArray()
        private set

    var monthArray = (1..12).map { "$it".padStart(2, '0') }.toTypedArray()
        private set

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogYearMonthSelectBinding.inflate(layoutInflater)
        binding.dialog = this
        binding.lifecycleOwner = this

        binding.yearPicker.value = 2

        return Dialog(requireActivity(), R.style.TransparentDialog).also {
            it.setContentView(binding.root)
        }
    }

    fun setTitle(title: String) {
        this.title = title
    }

    fun setPrimaryColor(@ColorInt color: Int) {
        _primaryColor.value = color
    }

    fun onSelect() {
        onSelectItem(yearArray[binding.yearPicker.value], monthArray[binding.monthPicker.value])
        dismiss()
    }

    override fun onResume() {
        super.onResume()
        requireContext().dialogResize(dialog)
        tag?.let {
            runCatching {
                val year = it.substring(0, 4)
                val month = it.substring(5, 7)

                binding.yearPicker.value = yearArray.indexOf(year)
                binding.monthPicker.value = monthArray.indexOf(month)
            }
        }
    }
}

