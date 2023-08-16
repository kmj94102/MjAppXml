package com.example.communication.repository

import com.example.communication.model.MyCalendarInfo
import com.example.communication.model.PlanTasksModify
import com.example.communication.model.ScheduleModifier
import com.example.communication.model.TaskUpdateItem
import kotlinx.coroutines.flow.Flow

interface CalendarRepository {

    /** 달력 정보 조회 **/
    fun fetchCalendarByMonth(
        year: Int,
        month: Int
    ): Flow<List<MyCalendarInfo>>

    /** 일정 등록 **/
    suspend fun insertSchedule(item: ScheduleModifier): Result<String>

    /** 계획 등록 **/
    suspend fun insertPlan(item: PlanTasksModify): Result<String>

    /** 일정 삭제 **/
    suspend fun deleteSchedule(id: Int)

    /** 계획 삭제 **/
    suspend fun deletePlanTasks(id: Int)

    /** 계획 업데이트 **/
    suspend fun updateTask(item: TaskUpdateItem): Result<Unit>

}