package com.example.mjappxml.ui.dialog

import coil.load
import com.example.communication.model.ElswordCharacter
import com.example.communication.model.ElswordQuestUpdate
import com.example.mjappxml.databinding.DialogElswordCounterUpdateBinding
import com.example.mjappxml.R
import com.example.mjappxml.common.dialogResize
import com.example.mjappxml.common.repeatOnStarted
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest

class ElswordCounterUpdateDialog(private val onItemUpdate: (String, String, Int) -> Unit)
    : BaseDialog<DialogElswordCounterUpdateBinding>(R.layout.dialog_elsword_counter_update) {

    private val _character = MutableStateFlow(value = ElswordCharacter.create())
    val character: StateFlow<ElswordCharacter> = _character

    private val _questTitle = MutableStateFlow(value = "")
    val questTitle: StateFlow<String> = _questTitle

    private val _max = MutableStateFlow(value = 0)
    val max: StateFlow<Int> = _max

    override fun initState() {
        super.initState()
        binding.dialog = this
        println("+++++ isComplete : ${_character.value.isComplete} / isOngoing : ${_character.value.isOngoing}")
        println("+++++ getIsComplete ${_character.value.getIsComplete()}")
        println("+++++ getIsProgress ${_character.value.getIsProgress()}")
        println("+++++ getIsNotProgress ${_character.value.getIsNotProgress()}")

        repeatOnStarted { _character.collectLatest { binding.invalidateAll() } }

        binding.radioGroup.setOnCheckedChangeListener { _, id ->
            _character.value = when (id) {
                binding.radioProgress.id -> {
                    _character.value.updateCopy("ongoing")
                }

                binding.radioComplete.id -> {
                    _character.value.updateCopy("complete")
                }

                else -> {
                    _character.value.updateCopy("notProgress")
                }
            }
        }

        binding.imageView.load(_character.value.image) {
            crossfade(true)
        }

        binding.slider.addOnChangeListener { _, value, _ ->
            _character.value = _character.value.copy(progress = value.toInt())
        }
    }

    fun onUpdate() {
        val name = _character.value.name
        val type = when(binding.radioGroup.checkedRadioButtonId) {
            binding.radioProgress.id -> {
                ElswordQuestUpdate.Ongoing
            }
            binding.radioComplete.id -> {
                ElswordQuestUpdate.Complete
            }
            else -> {
                ElswordQuestUpdate.Remove
            }
        }
        val progress = binding.slider.value.toInt()

        onItemUpdate(name, type, progress)
        dismiss()
    }

    fun setDialogInfo(
        character: ElswordCharacter,
        questTitle: String,
        max: Int
    ) {
        _character.value = character
        _questTitle.value = questTitle
        _max.value = max
    }

    fun getCount() = "${_character.value.progress}/${_max.value}"

    override fun onResume() {
        super.onResume()
        requireContext().dialogResize(dialog)
    }
}