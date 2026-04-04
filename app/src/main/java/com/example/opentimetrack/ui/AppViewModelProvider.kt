package com.example.opentimetrack.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.opentimetrack.ui.home.HomeViewModel
import com.example.opentimetrack.ui.time.TimeInstanceEntryViewModel
import com.example.opentimetrack.ui.time.TimeInstanceUpdateViewModel
import com.example.opentimetrack.ui.time.TimeInstanceViewModel
import com.example.opentimetrack.ui.type.TypeEntryViewModel
import com.example.opentimetrack.ui.type.TypeUpdateViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(timeApplication().container.typeRepository)
        }
        initializer {
            TypeEntryViewModel(timeApplication().container.typeRepository)
        }
        initializer {
            TypeUpdateViewModel(
                this.createSavedStateHandle(),
                timeApplication().container.typeRepository
            )
        }
        initializer {
            TimeInstanceViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                timeRepository = timeApplication().container.typeRepository
            )
        }
        initializer {
            TimeInstanceEntryViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                timeRepository = timeApplication().container.typeRepository
            )
        }
        initializer {
            TimeInstanceUpdateViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                repository = timeApplication().container.typeRepository
            )
        }
    }
}

fun CreationExtras.timeApplication(): TimeApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as TimeApplication)