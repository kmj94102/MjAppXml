package com.example.mjappxml.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.communication.model.HomeInfoResult
import com.example.communication.model.HomeParam
import com.example.communication.repository.HomeRepository
import com.example.mjappxml.common.getLastDayOfWeek
import com.example.mjappxml.common.getStartDayOfWeek
import com.example.mjappxml.common.getToday
import com.example.mjappxml.common.toStringFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
): ViewModel() {

    private val today = getToday()
    val yearMonth
        get() = getToday("yyyy년 MM월")

    private val sharedFlow = MutableSharedFlow<Pair<String, String>>(replay = 1)
    @OptIn(ExperimentalCoroutinesApi::class)
    val homeInfo: StateFlow<HomeInfoResult> = sharedFlow.flatMapLatest { (start, end) ->
        repository
            .fetchHomeInfo(HomeParam(start, end))
            .onStart {  }
            .onEach {  }
            .onCompletion {  }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(500L),
        initialValue = HomeInfoResult()
    )

    init {
        fetchHomeInfo()
    }

    private fun fetchHomeInfo() {
        val start = getStartDayOfWeek(today)?.toStringFormat() ?: return
        val end = getLastDayOfWeek(today)?.toStringFormat() ?: return

        sharedFlow.tryEmit(start to end)
    }

}