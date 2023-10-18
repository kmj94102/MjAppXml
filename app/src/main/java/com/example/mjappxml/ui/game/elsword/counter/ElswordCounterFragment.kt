package com.example.mjappxml.ui.game.elsword.counter

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.mjappxml.BaseViewModelFragment
import com.example.mjappxml.MainActivity
import com.example.mjappxml.databinding.FragmentElswordCounterBinding
import com.example.mjappxml.R
import com.example.mjappxml.common.repeatOnStarted
import com.example.mjappxml.ui.dialog.ElswordCounterUpdateDialog
import com.example.mjappxml.ui.dialog.SelectOneDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ElswordCounterFragment :
    BaseViewModelFragment<FragmentElswordCounterBinding, ElswordCounterViewModel>(R.layout.fragment_elsword_counter) {

    override val viewModel: ElswordCounterViewModel by viewModels()
    private lateinit var questSelectDialog : SelectOneDialog
    private lateinit var counterUpdateDialog: ElswordCounterUpdateDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

    }

    private fun initViews() = with(binding) {
        vm = viewModel
        fragment = this@ElswordCounterFragment

        layoutNetworkError.btnBack.setOnClickListener { onBack() }
        layoutNetworkError.cardReload.setOnClickListener { viewModel.fetchQuestDetailList() }

        questSelectDialog = SelectOneDialog {
            viewModel.updateSelectItem(it)
        }.also { it.setTitle("퀘스트 선택") }

        counterUpdateDialog = ElswordCounterUpdateDialog { name, type, progress ->
            viewModel.updateQuest(name, type, progress)
        }

        repeatOnStarted {
            viewModel.list.collectLatest {
                questSelectDialog.setPickerValue(
                    it.map { elswordQuestDetail -> elswordQuestDetail.name }.toTypedArray()
                )
            }
        }

        val adapter = ElswordCounterAdapter {
            val selectItem = viewModel.selectItem.value
            counterUpdateDialog.setDialogInfo(
                character = it,
                questTitle = selectItem.name,
                max = selectItem.max
            )
            counterUpdateDialog.show(parentFragmentManager, "updateCounter")
        }
        recyclerView.adapter = adapter

        repeatOnStarted {
            viewModel.selectItem.collectLatest {
                adapter.submitList(it.characters)
            }
        }
    }

    override fun updateNetworkErrorState(value: Boolean) {
        super.updateNetworkErrorState(value)

        binding.isError = value
    }

    fun goToAdd() {
        (activity as? MainActivity)?.goToPage(R.id.navigation_elsword_counter_add)
    }

    fun showQuestSelectDialog() {
        questSelectDialog.show(
            parentFragmentManager,
            "${viewModel.list.value.indexOfFirst { it.id == viewModel.selectItem.value.id}}"
        )
    }
}