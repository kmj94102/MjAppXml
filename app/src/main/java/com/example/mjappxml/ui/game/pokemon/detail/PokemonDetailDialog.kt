package com.example.mjappxml.ui.game.pokemon.detail

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.mjappxml.common.repeatOnStarted
import com.example.mjappxml.databinding.DialogPokemonInfoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonDetailDialog : DialogFragment() {

    private var number = "0001"
    private lateinit var binding: DialogPokemonInfoBinding
    private val viewModel: PokemonDetailViewModel by activityViewModels()
    private val adapter = PokemonDetailAdapter()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogPokemonInfoBinding.inflate(layoutInflater)
        initViews()

        viewModel.fetchPokemonDetailInfo(number)
        repeatOnStarted {
            viewModel.pokemonInfo.collect { adapter.submitList(it) }
        }

        return AlertDialog
            .Builder(requireActivity())
            .setView(binding.root)
            .create()
    }

    private fun initViews() = with(binding) {
        viewPager.adapter = adapter
        vm = viewModel
        dialog = this@PokemonDetailDialog

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.updatePage(position)
                invalidateAll()
            }
        })
    }

    fun setNumber(number: String) {
        this.number = number
    }

    fun onIsShinyButtonClick(view: View) {
        adapter.updateIsShiny(viewModel.toggleIsShiny())
        binding.invalidateAll()
    }


    fun changePage(page: Int) {
        runCatching {
            binding.viewPager.currentItem = page
            viewModel.updatePage(page)
            binding.invalidateAll()
        }
    }

    fun onDismiss(view: View) {
        dismiss()
    }

}