package com.example.mjappxml.ui.game.pokemon.generation.detail

import androidx.fragment.app.activityViewModels
import com.example.mjappxml.R
import com.example.mjappxml.common.dialogResize
import com.example.mjappxml.common.repeatOnStarted
import com.example.mjappxml.databinding.DialogGenerationInfoBinding
import com.example.mjappxml.ui.dialog.BaseDialog
import com.example.mjappxml.ui.game.pokemon.detail.PokemonDetailAdapter
import com.example.mjappxml.ui.game.pokemon.detail.PokemonDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GenerationDetailDialog(
    private val updateIsCatchItem: (Int, Boolean) -> Unit
) : BaseDialog<DialogGenerationInfoBinding>(R.layout.dialog_generation_info) {

    private var number = "0001"
    private var idx = 0
    private val viewModel: PokemonDetailViewModel by activityViewModels()
    private val adapter = PokemonDetailAdapter()
    private var isCatch = false

    override fun initState() = with(binding) {
        super.initState()

        viewModel.fetchPokemonDetailInfo(number)
        repeatOnStarted {
            viewModel.pokemonInfo.collect { adapter.submitList(it.reversed()) }
        }

        viewPager.adapter = adapter
        vm = viewModel
        dialog = this@GenerationDetailDialog
    }

    fun setNumber(number: String, idx: Int) {
        this.number = number
        this.idx = idx
    }

    override fun onResume() {
        super.onResume()
        requireContext().dialogResize(dialog)
        viewModel.updatePage(2)
        tag?.let {
            isCatch = it == "true"
            binding.isCatch = isCatch
        }
    }

    fun updateIsCatch() {
        viewModel.updateGenerationIsCatch(idx) {
            isCatch = it
            binding.isCatch = isCatch
            updateIsCatchItem(idx, isCatch)
        }
    }

}