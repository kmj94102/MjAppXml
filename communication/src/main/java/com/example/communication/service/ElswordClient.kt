package com.example.communication.service

import com.example.communication.model.ElswordCounterUpdateItem
import com.example.communication.model.ElswordQuest
import com.example.communication.model.ElswordQuestUpdate
import javax.inject.Inject

class ElswordClient @Inject constructor(
    private val  service: ElswordService
) {
    /** 퀘스트 등록 **/
    suspend fun insertQuest(quest: ElswordQuest) =
        runCatching { service.insertQuest(quest) }

    /** 퀘스트 삭제 **/
    suspend fun deleteQuest(id: Int) =
        runCatching { service.deleteQuest(id) }

    /** 퀘스트 조회 **/
    suspend fun fetchQuestList() =
        runCatching { service.fetchQuestList() }

    /** 퀘스트 상세 조회 **/
    suspend fun fetchQuestDetailList() =
        runCatching { service.fetchQuestDetailList() }

    /** 퀘스트 업데이트 **/
    suspend fun updateQuest(item: ElswordQuestUpdate) =
        runCatching { service.updateQuest(item) }

    /** 퀘스트 카운터 업데이트 **/
    suspend fun updateQuestCounter(item: ElswordCounterUpdateItem) =
        runCatching { service.updateQuestCounter(item) }

}