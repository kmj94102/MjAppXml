package com.example.communication.service

import com.example.communication.model.ElswordCounterUpdateItem
import com.example.communication.model.ElswordQuest
import com.example.communication.model.ElswordQuestDetailResult
import com.example.communication.model.ElswordQuestSimpleResult
import com.example.communication.model.ElswordQuestUpdate
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ElswordService {
    /** 퀘스트 등록 **/
    @POST("/elsword/insert/quest")
    suspend fun insertQuest(@Body item: ElswordQuest): String

    /** 퀘스트 삭제 **/
    @DELETE("/elsword/delete/quest")
    suspend fun deleteQuest(@Query("id") id: Int)

    /** 퀘스트 조회 **/
    @GET("/elsword/select/quest")
    suspend fun fetchQuestList(): List<ElswordQuestSimpleResult>

    /** 퀘스트 상세 조회 **/
    @GET("/elsword/select/quest/detail")
    suspend fun fetchQuestDetailList(): List<ElswordQuestDetailResult>

    /** 퀘스트 업데이트 **/
    @POST("/elsword/update/quest")
    suspend fun updateQuest(@Body item: ElswordQuestUpdate)

    /** 퀘스트 카운터 업데이트 **/
    @POST("/elsword/update/counter")
    suspend fun updateQuestCounter(@Body item: ElswordCounterUpdateItem): Int
}