package com.example.opentimetrack.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.opentimetrack.data.entity.Type
import com.example.opentimetrack.data.repository.TimeRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(
    typeRepository: TimeRepository,
) : ViewModel() {
    val homeUiState: StateFlow<HomeUiState> =
        typeRepository.getAllTypesStream().map { types ->
            HomeUiState(
                typeList = types.map { type ->
                    TypeWithTotalTime(
                        type = type,
                        totalTime = typeRepository.getTypeTimeSum(type)
                    )
                }
            )
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = HomeUiState()
            )
}

class TypeWithTotalTime(
    val type: Type,
    val totalTime: Int
) {
    fun timeInHours(): String {
        return "%.1f".format(totalTime / 60.0)
    }
}

data class HomeUiState(
    val typeList: List<TypeWithTotalTime> = listOf()
)