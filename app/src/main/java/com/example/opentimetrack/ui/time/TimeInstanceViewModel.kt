package com.example.opentimetrack.ui.time

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.opentimetrack.data.entity.TimeInstance
import com.example.opentimetrack.data.repository.TimeRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class TimeInstanceViewModel(
    savedStateHandle: SavedStateHandle,
    private val timeInstanceRepository: TimeRepository
) : ViewModel() {
    val typeId: Int = checkNotNull(savedStateHandle[TimeInstanceDestination.typeIdArg])

    var timeInstanceUiState: StateFlow<TimeInstancesUiState> =
        timeInstanceRepository.getAllTimeInstancesStream(typeId).map { TimeInstancesUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = TimeInstancesUiState()
            )

}


data class TimeInstancesUiState(val timeInstanceList: List<TimeInstance> = listOf())
