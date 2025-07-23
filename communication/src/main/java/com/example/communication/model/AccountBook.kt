package com.example.communication.model

import com.example.communication.util.formatAmountWithSign
import java.text.SimpleDateFormat
import java.util.Locale

data class AccountBookItem(
    val id: Int = 0,
    val date: String,
    val dateOfWeek: String,
    val amount: Int,
    val usageType: String,
    val whereToUse: String
)

data class AccountBookInsertItem(
    val id: Int = 0,
    val date: String,
    val dateOfWeek: String,
    var amount: Int,
    val usageType: String,
    var whereToUse: String,
    var isAddFrequently: Boolean,
    val isIncome: Boolean = true,
) {
    fun updateDate(dateValue: String): AccountBookInsertItem {
        val sdfInput = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
        val sdfOutput = SimpleDateFormat("E", Locale.getDefault())

        val date = sdfInput.parse(dateValue) ?: return this

        return copy(
            date = dateValue,
            dateOfWeek = sdfOutput.format(date.time)
        )
    }

    fun checkValidity(): String = when {
        date.isEmpty() -> "날짜를 확인해 주세요"
        amount == 0 -> "금액을 확인해 주세요"
        usageType.isEmpty() -> "사용처를 선택해 주세요"
        whereToUse.isEmpty() -> "사용 내용을 입력해 주세요"
        else -> ""
    }

    fun uploadFormat(isIncome: Boolean) = copy(
        date = "${date.replace(".", "-")}T10:00:00.000Z",
        amount = if (isIncome) amount else amount * -1
    )

    companion object {
        fun initItem(date: String = "") = AccountBookInsertItem(
            date = date,
            dateOfWeek = "",
            amount = 0,
            usageType = "meal",
            whereToUse = "",
            isAddFrequently = false
        )
    }
}

data class AccountBookMainInfo(
    val thisMonthSummary: ThisMonthSummary,
    val lastMonthAnalysis: LastMonthAnalysis,
    val thisYearSummary: List<ThisYearSummaryItem>
) {
    companion object {
        fun create() = AccountBookMainInfo(
            thisMonthSummary = ThisMonthSummary.create(),
            lastMonthAnalysis = LastMonthAnalysis.create(),
            thisYearSummary = listOf()
        )
    }
}

data class ThisMonthSummary(
    val startDate: String,
    val endDate: String,
    val income: Int,
    val expenditure: Int,
    val difference: Int
) {
    companion object {
        fun create() = ThisMonthSummary(
            startDate = "",
            endDate = "",
            income = 0,
            expenditure = 0,
            difference = 0
        )
    }

    fun getDate() = "$startDate ~ $endDate"

    fun getFormatIncome() = income.formatAmountWithSign()
    fun getFormatExpenditure() = expenditure.formatAmountWithSign()
    fun getFormatDifference() = difference.formatAmountWithSign()
}

data class LastMonthAnalysis(
    val start: String,
    val end: String,
    val result: List<LastMonthAnalysisItem>
) {
    companion object {
        fun create() = LastMonthAnalysis(
            start = "",
            end = "",
            result = listOf()
        )
    }

    fun getDate() = "$start ~ $end"
}

data class LastMonthAnalysisItem(
    val usageType: String,
    val amount: Int
)

data class ThisYearSummaryItem(
    val month: Int,
    val startDate: String,
    val endDate: String,
    val info: Int
) {
    fun getAmount() = info.formatAmountWithSign()
}

data class DateConfiguration(
    val date: String,
    val baseDate: Int
) {
    companion object {
        fun create(date: String, baseDate: Int) = DateConfiguration(
            date = "${date.replace(".", "-")}T10:00:00.000Z",
            baseDate = baseDate
        )
    }
}

data class AccountBookDetailInfo(
    val startDate: String,
    val endDate: String,
    val income: Int,
    val expenditure: Int,
    val list: List<AccountBookItem>
) {
    fun getDate() = "$startDate ~ $endDate"

    fun modifyDateFormat() = AccountBookDetailInfo(
        startDate = startDate,
        endDate = endDate,
        income = income,
        expenditure = expenditure,
        list = list.map {
            it.copy(date = it.date.replace("-", "."))
        }
    )

    fun getPeriod() = "$startDate ~ $endDate"

    fun getFormatIncome() = income.formatAmountWithSign()

    fun getFormatExpenditure() = expenditure.formatAmountWithSign()

    companion object {
        fun init() = AccountBookDetailInfo(
            startDate = "",
            endDate = "",
            income = 0,
            expenditure = 0,
            list = emptyList()
        )
    }
}

data class FixedAccountBook(
    val id: Int,
    val date: String,
    val amount: Int,
    val usageType: String,
    var whereToUse: String,
    val isIncome: Boolean
) {
    fun getDateText() = "${date.padStart(2, '0')}일"

    fun checkValidity() = when {
        amount == 0 -> throw Exception("금액을 입력해 주세요.")
        usageType.isEmpty() -> throw Exception("사용처를 선택해 주세요.")
        whereToUse.isEmpty() -> throw Exception("사용 내용을 입력해 주세요")
        else -> this
    }

    fun toAccountBookInsertItem(
        yearMonth: String
    ) = AccountBookInsertItem(
        date = "",
        dateOfWeek = "",
        amount = amount,
        usageType = usageType,
        whereToUse = whereToUse,
        isAddFrequently = false
    ).updateDate("$yearMonth.$date")

    companion object {
        fun create() = FixedAccountBook(
            id = 0,
            date = "01",
            amount = 0,
            usageType = "",
            whereToUse = "",
            isIncome = true
        )
    }
}

data class FrequentlyItem(
    val id: Int,
    val amount: Int,
    val usageType: String,
    val whereToUse: String
)