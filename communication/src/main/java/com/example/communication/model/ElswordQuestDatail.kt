package com.example.communication.model

import com.google.gson.annotations.SerializedName

data class ElswordQuestDetailResult(
    val id: Int?,
    val max: Int?,
    val name: String?,
    @SerializedName("character")
    val characters: List<ElswordCharacter>?
) {
    fun toElswordQuestDetail(): ElswordQuestDetail? {
        return ElswordQuestDetail(
            id = id ?: return null,
            max = max ?: return null,
            name = name ?: return null,
            characters = characters?.toMutableList() ?: return null
        )
    }
}

data class ElswordQuestDetail(
    val id: Int,
    val max: Int,
    val name: String,
    val characters: MutableList<ElswordCharacter>
) {
    fun getCharactersWithGroup() = characters.groupBy { it.group }

    fun getProgress(): String {
        val progress = characters.filter { it.isComplete }.size / 56.0 * 100
        return if (id == -1) {
            ""
        } else if (progress % 1 == 0.0) {
            String.format("진행도 : %.0f", progress)
        } else {
            String.format("진행도 : %.2f", progress)
        }
    }

    fun getQuestUpdateInfo(characterName: String): ElswordQuestUpdateInfo {
        val index = characters.indexOfFirst { it.name == characterName }
        return ElswordQuestUpdateInfo(
            max = max,
            questName = name,
            characterName = characterName,
            image = characters[index].image,
            progress = characters[index].progress,
            type = when {
                characters[index].isComplete -> ElswordQuestUpdate.Complete
                characters[index].isOngoing -> ElswordQuestUpdate.Ongoing
                else -> ElswordQuestUpdate.Remove
            }
        )
    }

    companion object {
        fun init() = ElswordQuestDetail(
            id = -1,
            max = 0,
            name = "",
            characters = mutableListOf()
        )
    }
}

data class ElswordCharacter(
    val name: String,
    val image: String,
    val group: String,
    val isComplete: Boolean,
    val isOngoing: Boolean,
    val progress: Int
) {
    fun updateCopy(type: String): ElswordCharacter {
        return when (type) {
            "complete" -> {
                this.copy(
                    isComplete = true,
                    isOngoing = false
                )
            }

            "ongoing" -> {
                this.copy(
                    isOngoing = true,
                    isComplete = false
                )
            }

            else -> {
                this.copy(
                    isComplete = false,
                    isOngoing = false
                )
            }
        }
    }

    fun getIsNotProgress() = isComplete.not() && isOngoing.not()

    fun getIsProgress() = isComplete.not() && isOngoing

    fun getIsComplete() = isComplete && isOngoing.not()

    companion object {
        fun create() = ElswordCharacter(
            name = "",
            image = "",
            group = "",
            isComplete = false,
            isOngoing = false,
            progress = 0
        )
    }
}