package com.example.opentimetrack.ui.type

import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.opentimetrack.data.entity.Type
import com.example.opentimetrack.data.repository.TimeRepository

class TypeEntryViewModel(private val typeRepository: TimeRepository) : ViewModel() {

    var typeUiState by mutableStateOf(TypeUiState())
        private set

    fun updateUiState(typeDetails: Type) {
        typeUiState =
            TypeUiState(typeDetails = typeDetails, isEntryValid = validateInput(typeDetails))
    }

    private fun validateInput(uiState: Type = typeUiState.typeDetails): Boolean {
        return with(uiState) {
            name.isNotBlank()
        }
    }

    suspend fun saveItem() {
        if (validateInput()) {
            typeRepository.insertType(typeUiState.typeDetails)
        }
    }
}

data class TypeUiState(
    val typeDetails: Type = Type(id = 0, name = ""),
    val isEntryValid: Boolean = false
)

fun Type.toItemUiState(isEntryValid: Boolean = false): TypeUiState =
    TypeUiState(
        typeDetails = this,
        isEntryValid = isEntryValid
    )
