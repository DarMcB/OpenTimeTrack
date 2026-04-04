package com.example.opentimetrack.ui.time

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.opentimetrack.R
import com.example.opentimetrack.data.entity.TimeInstance
import com.example.opentimetrack.data.entity.Type
import com.example.opentimetrack.data.repository.TimeRepository
import com.example.opentimetrack.ui.type.TypeUiState
import com.example.opentimetrack.ui.type.toItemUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TimeInstanceViewModel(
    savedStateHandle: SavedStateHandle,
    private val timeRepository: TimeRepository
) : ViewModel() {
    val typeId: Int = checkNotNull(savedStateHandle[TimeInstanceDestination.typeIdArg])

    var timeInstanceUiState: StateFlow<TimeInstancesUiState> =
        timeRepository.getAllTimeInstancesStream(typeId).map { TimeInstancesUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = TimeInstancesUiState()
            )
    var typeUiState by mutableStateOf(TypeUiState())
        private set

    init {
        viewModelScope.launch {
            typeUiState = timeRepository.getTypeStream(typeId)
                .filterNotNull()
                .first()
                .toItemUiState(true)
        }
    }
}


data class TimeInstancesUiState(val timeInstanceList: List<TimeInstance> = listOf())
