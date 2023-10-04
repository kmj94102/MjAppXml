package com.example.mjappxml.ui.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.example.communication.model.Recurrence
import com.example.mjappxml.R
import com.example.mjappxml.common.dialogResize
import com.example.mjappxml.databinding.DialogSelectRecurrenceTypeBinding

class SelectRecurrenceTypeDialog(
    private val onSelectItem: (String) -> Unit
) : DialogFragment(){

    private lateinit var binding: DialogSelectRecurrenceTypeBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogSelectRecurrenceTypeBinding.inflate(layoutInflater)
        binding.dialog = this
        binding.lifecycleOwner = this

        return Dialog(requireActivity(), R.style.TransparentDialog).also {
            it.setContentView(binding.root)
        }
    }

    fun onSelect() = with(binding) {
        val type = when(radioGroup.selectRadiobuttonId) {
            radioDaily.id -> Recurrence.Daily.originName
            radioWeekly.id -> Recurrence.Weekly.originName
            radioMonthly.id -> Recurrence.Monthly.originName
            radioYearly.id -> Recurrence.Yearly.originName
            else -> Recurrence.NoRecurrence.originName
        }

        onSelectItem(type)
        dismiss()
    }

    override fun onResume() {
        super.onResume()
        requireContext().dialogResize(dialog)
        tag?.let {
            binding.radioGroup.initSelect(it)
        }
    }

}