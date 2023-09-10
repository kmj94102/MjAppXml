package com.example.communication.repository

import com.example.communication.model.ElswordCounterUpdateItem
import com.example.communication.model.ElswordQuest
import com.example.communication.model.ElswordQuestDetail
import com.example.communication.model.ElswordQuestSimple
import com.example.communication.model.ElswordQuestUpdate
import kotlinx.coroutines.flow.Flow

interface ElswordRepository {
    /** 퀘스트 등록 */
    suspend fun insertQuest(quest: ElswordQuest): Result<String>

    /** 퀘스트 삭제 **/
    suspend fun deleteQuest(id: Int): Result<Unit>

    /** 퀘스트 조회 **/
    fun fetchQuestList(): Flow<List<ElswordQuestSimple>>

    /** 퀘스트 상세 조회 **/
    fun fetchQuestDetailList(): Flow<List<ElswordQuestDetail>>

    /** 퀘스트 업데이트 **/
    suspend fun updateQuest(item: ElswordQuestUpdate): Result<Unit>

    /** 퀘스트 카운터 업데이트 **/
    suspend fun updateQuestCounter(item: ElswordCounterUpdateItem): Int

}