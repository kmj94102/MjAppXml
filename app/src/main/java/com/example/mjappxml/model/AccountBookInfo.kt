package com.example.mjappxml.model

import com.example.communication.model.AccountBookItem
import com.example.communication.util.formatAmountWithSign
import com.example.mjappxml.ui.accountbook.IncomeExpenditureType

sealed class AccountBookInfo(val type: Int) {
    data class Item(
        val id: Int = 0,
        val date: String,
        val dateOfWeek: String,
        val amount: Int,
        val usageType: String,
        val whereToUse: String
    ) : AccountBookInfo(ITEM) {
        fun formatAmount() = amount.formatAmountWithSign()

        fun isIncome() = amount > 0

        fun getTypeImage() = IncomeExpenditureType.getImageByType(usageType)
    }

    data class Date(val date: String) : AccountBookInfo(DATE)

    companion object {
        const val ITEM = 0
        const val DATE = 1

        private fun fromAccountItem(item: AccountBookItem) = Item(
            id = item.id,
            date = item.date,
            dateOfWeek = item.dateOfWeek,
            amount = item.amount,
            usageType = item.usageType,
            whereToUse = item.whereToUse
        )

        fun List<AccountBookItem>.convert(): List<AccountBookInfo> {
            var lastDate = ""
            val result = mutableListOf<AccountBookInfo>()
            forEach {
                if (lastDate != it.date) {
                    lastDate = it.date
                    result.add(Date(it.date.replace("-", ".")))
                }
                result.add(fromAccountItem(it))
            }

            return result
        }

    }
}
