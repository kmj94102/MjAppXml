package com.example.mjappxml.ui.game.pokemon.dex

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.mjappxml.databinding.DialogPokemonSearchBinding

class PokemonSearchDialog(
    private val onSearch: (String) -> Unit
) : DialogFragment() {

    private lateinit var binding: DialogPokemonSearchBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogPokemonSearchBinding.inflate(layoutInflater)
        binding.dialog = this

        return AlertDialog
            .Builder(requireActivity())
            .setView(binding.root)
            .create()
    }

    fun onDismiss() {
        dismiss()
    }

    fun onSearchClick() {
        val name = binding.editName.text.toString().trim()
        onSearch(name)
        onDismiss()
    }

}