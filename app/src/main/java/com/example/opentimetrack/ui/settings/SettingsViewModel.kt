package com.example.opentimetrack.ui.settings

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.opentimetrack.data.csv.CsvConverter
import com.example.opentimetrack.data.entity.TimeInstance
import com.example.opentimetrack.data.repository.TimeRepository
import com.example.opentimetrack.ui.time.TimeInstanceDestination
import com.example.opentimetrack.ui.time.TimeInstanceUiState
import com.example.opentimetrack.ui.time.TimeInstancesUiState
import com.example.opentimetrack.ui.type.TypeUiState
import com.example.opentimetrack.ui.type.toItemUiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SettingsViewModel(
    val savedStateHandle: SavedStateHandle,
    private val timeRepository: TimeRepository
) : ViewModel() {
    val typeId: Int = checkNotNull(savedStateHandle[TimeInstanceDestination.typeIdArg])

    /*csv file creation:
        loop:
            format:
                date(dd/mm/yyyy or mm/dd/yyyy or yyyy/mm/dd or unixtimestamp),
                time(in minutes),
                typename

            TODO: user shall be able to choose what to include in export
     */

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

    fun exportAsCsv(context: Context) {
        val csv: CsvConverter = CsvConverter()
        csv.exportAsCSV()
    }

}