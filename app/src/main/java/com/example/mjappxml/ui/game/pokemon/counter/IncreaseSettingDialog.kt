package com.example.mjappxml.ui.game.pokemon.counter

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.mjappxml.databinding.DialogIncreaseSettingBinding

class IncreaseSettingDialog(
    val increase: Int,
    private val onSetting: (Int) -> Unit
) : DialogFragment() {

    private lateinit var binding: DialogIncreaseSettingBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogIncreaseSettingBinding.inflate(layoutInflater)
        binding.dialog = this
        binding.lifecycleOwner = this

        return AlertDialog
            .Builder(requireActivity())
            .setView(binding.root)
            .create()
    }

    fun onDismissClick() {
        dismiss()
    }

    fun onSettingClick() {
        onSetting(binding.editText.text.toString().toInt())
        dismiss()
    }

}