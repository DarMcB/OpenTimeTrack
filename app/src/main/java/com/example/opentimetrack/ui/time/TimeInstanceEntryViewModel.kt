package com.example.opentimetrack.ui.time

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.opentimetrack.data.entity.TimeInstance
import com.example.opentimetrack.data.repository.TimeRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TimeInstanceEntryViewModel(
    val savedStateHandle: SavedStateHandle,
    private val timeRepository: TimeRepository
) : ViewModel() {

    val typeId: Int = checkNotNull(savedStateHandle[TimeInstanceEntryDestination.typeIdArg])
    var uiState by mutableStateOf(TimeInstanceUiState(TimeInstanceDetails(typeId = typeId)))
        private set



    fun updateUiState(timeInstanceDetails: TimeInstanceDetails) {
        uiState =
            TimeInstanceUiState(timeInstanceDetails = timeInstanceDetails, isEntryValid = validateInput(timeInstanceDetails))
    }

    private fun validateInput(timeInstanceUiState: TimeInstanceDetails = uiState.timeInstanceDetails): Boolean {
        return with(timeInstanceUiState) {
            date.isNotBlank() && time.isNotBlank()
        }
    }

    suspend fun saveTimeInstance() {
        if (validateInput())
            timeRepository.insertTimeInstance(uiState.timeInstanceDetails.toTimeInstance())
    }
}

data class TimeInstanceUiState(
    val timeInstanceDetails: TimeInstanceDetails = TimeInstanceDetails(),
    val isEntryValid: Boolean = false
)

data class TimeInstanceDetails(
    val id: Int = 0,
    val typeId: Int = 0,
    val date: String = "",
    val time: String = ""
)

fun TimeInstanceDetails.toTimeInstance(): TimeInstance =
    TimeInstance(
        id = id,
        typeId = typeId,
        date = date.toLongOrNull() ?: 0,
        time = time.toIntOrNull() ?: 0,
    )

fun TimeInstance.toTimeInstanceDetails(): TimeInstanceDetails =
    TimeInstanceDetails(
        id = id,
        typeId = typeId,
        date = date.toString(),
        time = time.toString()
    )

fun TimeInstance.toTimeInstanceUiState(isEntryValid: Boolean = false):
    TimeInstanceUiState =
        TimeInstanceUiState(
            timeInstanceDetails = this.toTimeInstanceDetails(),
            isEntryValid = isEntryValid
        )

fun convertMillisToDate(millis: String): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return try {
        formatter.format(Date(millis.toLong()))
    } catch (_: Exception) {
        return ""
    }

}
