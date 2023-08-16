package com.example.communication.model

data class ElswordQuestUpdate(
    val id: Int,
    val name: String,
    val type: String,
    val progress: Int = 0
) {
    companion object {
        const val Complete = "complete"
        const val Ongoing = "ongoing"
        const val Remove = "remove"
    }
}

data class ElswordQuestUpdateInfo(
    val max: Int,
    val questName: String,
    val characterName: String,
    val image : String,
    val type: String,
    val progress: Int
) {
    companion object {
        fun create() = ElswordQuestUpdateInfo(
            max = 0,
            questName = "",
            characterName = "",
            image = "",
            type = ElswordQuestUpdate.Remove,
            progress = 0
        )
    }
}
