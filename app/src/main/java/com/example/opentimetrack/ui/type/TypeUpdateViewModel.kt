package com.example.opentimetrack.ui.type

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.opentimetrack.data.entity.Type
import com.example.opentimetrack.data.repository.TimeRepository
import com.example.opentimetrack.ui.timeApplication
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class TypeUpdateViewModel(
    savedStateHandle: SavedStateHandle,
    private val typeRepository: TimeRepository,
    typeId: Int
) : ViewModel() {
    var typeUiState by mutableStateOf(TypeUiState())
        private set

    companion object {
        fun factory(typeId: Int) = viewModelFactory {
            initializer {
                TypeUpdateViewModel(
                    savedStateHandle = this.createSavedStateHandle(),
                    typeRepository = timeApplication().container.typeRepository,
                    typeId = typeId
                )
            }
        }
    }

    init {
        load(typeId)
    }

    fun load(typeId: Int) {
        viewModelScope.launch {
            typeUiState = typeRepository.getTypeStream(typeId)
                .filterNotNull()
                .first()
                .toItemUiState(true)
        }
    }

    suspend fun updateType() {
        if (validateInput(uiState = typeUiState.typeDetails)) {
            typeRepository.updateType(typeUiState.typeDetails)
        }
    }

    suspend fun deleteType() {
        typeRepository.deleteTimeInstances(typeUiState.typeDetails.id)
        typeRepository.deleteType(typeUiState.typeDetails)
    }

    fun updateUiState(typeDetails: Type) {
        typeUiState = TypeUiState(typeDetails = typeDetails, isEntryValid = validateInput(typeDetails))
    }

    private fun validateInput(uiState: Type = typeUiState.typeDetails) : Boolean {
        return with(uiState) {
            name.isNotBlank()
        }
    }
}