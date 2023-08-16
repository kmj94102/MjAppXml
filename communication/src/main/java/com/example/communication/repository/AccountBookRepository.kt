package com.example.communication.repository

import com.example.communication.model.AccountBookDetailInfo
import com.example.communication.model.AccountBookInsertItem
import com.example.communication.model.AccountBookMainInfo
import com.example.communication.model.DateConfiguration
import com.example.communication.model.FixedAccountBook
import com.example.communication.model.FrequentlyItem
import kotlinx.coroutines.flow.Flow

interface AccountBookRepository {
    /** 가계부 조회 **/
    fun fetchAccountBookInfo(
        dateConfiguration: DateConfiguration
    ): Flow<AccountBookMainInfo>

    /** 가계부 등록 **/
    suspend fun insertNewAccountBookItem(
        item: AccountBookInsertItem,
        isIncome: Boolean
    ): Result<String>

    /** 이번달 상세 조회 **/
    fun fetchThisMonthDetail(
        dateConfiguration: DateConfiguration
    ): Flow<AccountBookDetailInfo>

    /** 고정 내역 추가 **/
    suspend fun insertFixedAccountBookItem(
        item: FixedAccountBook
    ): Result<String>

    /** 고정 내역 삭제 **/
    suspend fun deleteAccountBookItem(id: Int): Result<Unit>

    /** 고정 내역 조회 **/
    fun fetchFixedAccountBook(): Flow<List<FixedAccountBook>>

    /** 즐겨 찾기 조회 **/
    fun fetchFrequentlyAccountBookItems(): Flow<List<FrequentlyItem>>

    /** 즐겨 찾기 삭제 **/
    suspend fun deleteFrequentlyAccountBookItem(id: Int): Result<Unit>

}