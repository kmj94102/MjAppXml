package com.example.communication.repository

import com.example.communication.model.AccountBookInsertItem
import com.example.communication.model.DateConfiguration
import com.example.communication.model.FixedAccountBook
import com.example.communication.model.getFailureThrow
import com.example.communication.model.printStackTrace
import com.example.communication.service.AccountBookClient
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AccountBookRepositoryImpl @Inject constructor(
    private val client: AccountBookClient
) : AccountBookRepository {
    /** 가계부 조회 **/
    override fun fetchAccountBookInfo(dateConfiguration: DateConfiguration) = flow {
        client.fetchAccountBookInfo(dateConfiguration)
            .onSuccess { emit(it) }
            .getFailureThrow()
    }

    /** 가계부 등록 **/
    override suspend fun insertNewAccountBookItem(
        item: AccountBookInsertItem,
        isIncome: Boolean
    ) = client.insertNewAccountBookItem(item.uploadFormat(isIncome)).printStackTrace()

    /** 이번달 상세 조회 **/
    override fun fetchThisMonthDetail(dateConfiguration: DateConfiguration) = flow {
        client
            .fetchThisMonthDetail(dateConfiguration)
            .onSuccess { emit(it) }
            .getFailureThrow()
    }

    /** 고정 내역 등록 **/
    override suspend fun insertFixedAccountBookItem(item: FixedAccountBook) = runCatching {
        client.insertFixedAccountBookItem(item.checkValidity()).getOrThrow()
    }

    /** 고정 내역 삭제 **/
    override suspend fun deleteAccountBookItem(id: Int) =
        client.deleteAccountBookItem(id).printStackTrace()

    /** 거정 내역 조회 **/
    override fun fetchFixedAccountBook() = flow {
        client
            .fetchFixedAccountBook()
            .onSuccess { emit(it) }
            .getFailureThrow()
    }

    /** 즐겨 찾기 아이템 조회 **/
    override fun fetchFrequentlyAccountBookItems() = flow {
        client
            .fetchFrequentlyAccountBookItems()
            .onSuccess { emit(it) }
            .getFailureThrow()
    }

    /** 즐겨 찾기 삭제 **/
    override suspend fun deleteFrequentlyAccountBookItem(id: Int) =
        client.deleteFrequentlyAccountBookItem(id).printStackTrace()

}