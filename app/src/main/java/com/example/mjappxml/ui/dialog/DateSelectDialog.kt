package com.example.mjappxml.ui.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.mjappxml.R
import com.example.mjappxml.common.dialogResize
import com.example.mjappxml.databinding.DialogDateSelectBinding
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Calendar

class DateSelectDialog(
    private val onSelectItem: (String) -> Unit
) : DialogFragment() {
    private lateinit var binding: DialogDateSelectBinding
    var title = ""
        private set

    val yearArray = (2020..2040).map { "$it" }.toTypedArray()

    val monthArray = (1..12).map { "$it".padStart(2, '0') }.toTypedArray()

    private val _dayArray = MutableStateFlow(
        (1..31).map { "$it".padStart(2, '0') }.toTypedArray()
    )
    val dayArray: StateFlow<Array<String>> = _dayArray

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogDateSelectBinding.inflate(layoutInflater)
        binding.dialog = this
        binding.lifecycleOwner = this

        initViews()

        return Dialog(requireActivity(), R.style.TransparentDialog).also {
            it.setContentView(binding.root)
        }
    }

    fun initViews() {
        binding.yearPicker.setOnValueChangedListener { _, _, value ->
            val year = yearArray[value]
            val month = monthArray[binding.monthPicker.value]
            _dayArray.value = getDayList(year, month)
        }

        binding.monthPicker.setOnValueChangedListener { _, _, value ->
            val year = yearArray[binding.yearPicker.value]
            val month = monthArray[value]
            _dayArray.value = getDayList(year, month)
        }
    }

    fun setTitle(title: String = "날짜 선택") {
        this.title = title
    }

    fun onSelect() {
        onSelectItem(
            "${yearArray[binding.yearPicker.value]}.${monthArray[binding.monthPicker.value]}.${_dayArray.value[binding.dayPicker.value]}"
        )
        dismiss()
    }

    private fun getDayList(year: String, month: String): Array<String> {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.YEAR, year.toInt())
            set(Calendar.MONTH, month.toInt() - 1)
        }

        return (1..calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
            .map { it.toString().padStart(2, '0') }
            .toTypedArray()
    }

    override fun onResume() {
        super.onResume()
        requireContext().dialogResize(dialog)
        tag?.let(::initSelect)
    }

    private fun initSelect(date: String) = runCatching {
        val year = date.substring(0, 4)
        val month = date.substring(5, 7)
        val day = date.substring(8, 10)
        _dayArray.value = getDayList(year, month)

        binding.yearPicker.value = yearArray.indexOf(year)
        binding.monthPicker.value = monthArray.indexOf(month)
        binding.dayPicker.value = dayArray.value.indexOf(day)
    }
}

