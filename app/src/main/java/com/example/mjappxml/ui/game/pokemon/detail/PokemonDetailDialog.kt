package com.example.mjappxml.ui.game.pokemon.detail

import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.mjappxml.R
import com.example.mjappxml.common.dialogResize
import com.example.mjappxml.common.repeatOnStarted
import com.example.mjappxml.common.toast
import com.example.mjappxml.databinding.DialogPokemonInfoBinding
import com.example.mjappxml.ui.dialog.BaseDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonDetailDialog(
    private val updateIsCatchItem: (String, Boolean) -> Unit
) : BaseDialog<DialogPokemonInfoBinding>(R.layout.dialog_pokemon_info) {

    private var number = "0001"
    private val viewModel: PokemonDetailTempViewModel by activityViewModels()
    private val adapter = PokemonDetailAdapter()
    private var isCatch = false

    override fun initState() = with(binding) {
        super.initState()

        viewModel.fetchPokemonDetailInfo(number)
        repeatOnStarted {
            viewModel.pokemonInfo.collect { adapter.submitList(it) }
        }

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

    fun onIsShinyButtonClick() {
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

    fun insertCounter() {
        viewModel.insertPokemonCounter(number)
        requireContext().toast("${viewModel.pokemonInfo.value[0].name} 등록 완료")
    }

    override fun onResume() {
        super.onResume()
        requireContext().dialogResize(dialog)
        tag?.let {
            isCatch = it == "true"
            binding.isCatch = isCatch
        }
    }

    fun updateIsCatch() {
        isCatch = isCatch.not()
        viewModel.updateIsCatch(number, isCatch)
        binding.isCatch = isCatch
        updateIsCatchItem(number, isCatch)
    }

}