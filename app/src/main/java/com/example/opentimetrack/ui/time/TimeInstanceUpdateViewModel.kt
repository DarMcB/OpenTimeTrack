package com.example.opentimetrack.ui.time

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.opentimetrack.data.repository.TimeRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class TimeInstanceUpdateViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: TimeRepository
) : ViewModel() {

    val timeInstanceId: Int = checkNotNull(savedStateHandle[TimeInstanceUpdateDestination.timeInstanceIdArg])

    var uiState by mutableStateOf(TimeInstanceUiState())

    init {
        viewModelScope.launch {
            uiState = repository.getTimeInstanceStream(timeInstanceId)
                .filterNotNull()
                .first()
                .toTimeInstanceUiState(true)
        }
    }

    suspend fun updateTimeInstance() {
        if (validateInput(uiState.timeInstanceDetails)) {
            repository.updateTimeInstance(uiState.timeInstanceDetails.toTimeInstance())
            }
    }

    suspend fun deleteTimeInstance() {
        repository.deleteTimeInstance(uiState.timeInstanceDetails.toTimeInstance())
    }

    fun updateUiState(timeInstanceDetails: TimeInstanceDetails) {
        uiState =  TimeInstanceUiState(
            timeInstanceDetails = timeInstanceDetails,
            isEntryValid = validateInput(timeInstanceDetails)
        )
    }

    private fun validateInput(timeInstanceDetails: TimeInstanceDetails = uiState.timeInstanceDetails) : Boolean {
        return with(timeInstanceDetails) {
            date.isNotBlank() && time.isNotBlank()
        }
    }
}