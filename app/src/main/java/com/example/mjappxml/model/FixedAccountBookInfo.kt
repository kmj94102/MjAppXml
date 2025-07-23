package com.example.mjappxml.model

import android.os.Parcelable
import com.example.communication.model.FixedAccountBook
import com.example.communication.util.formatAmountWithSign
import com.example.mjappxml.ui.accountbook.IncomeExpenditureType
import kotlinx.parcelize.Parcelize

@Parcelize
data class FixedAccountBookInfo(
    val id: Int = 0,
    val amount: Int = 0,
    val usageType: String = IncomeExpenditureType.Meal.type,
    val whereToUse: String = "",
    val isIncome: Boolean = true,
    val isSelect: Boolean = false
): Parcelable {
    fun getFormatAmount() = amount.formatAmountWithSign()

    fun getUsageTypeRes() = IncomeExpenditureType.getImageByType(usageType)
}

fun FixedAccountBook.mapper() = FixedAccountBookInfo(
    id = id,
    amount = amount,
    usageType = usageType,
    whereToUse = whereToUse,
    isIncome = isIncome
)

fun List<FixedAccountBook>.mapper(): List<FixedAccountBookInfo> =
    map { it.mapper() }