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
        typeRepository.getAllTypesStream().map { HomeUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = HomeUiState()
            )
}

data class HomeUiState(
    val typeList: List<Type> = listOf()
)