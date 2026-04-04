package com.example.opentimetrack.ui.time

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import com.example.opentimetrack.R
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.opentimetrack.ui.AppTopBar
import com.example.opentimetrack.ui.AppViewModelProvider
import com.example.opentimetrack.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

object TimeInstanceEntryDestination : NavigationDestination {
    override val route = "time_instance_entry"
    override val titleRes = R.string.time_instance_entry_title
    const val typeIdArg = "typeId"
    val routeArg = "$route/{$typeIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeInstanceEntryScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    viewModel: TimeInstanceEntryViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()

    Card(
        modifier = modifier.padding()
    ) {
        TimeInstanceEntryBody(
            uiState = viewModel.uiState,
            onValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveTimeInstance()
                    navigateBack
                }
            },
            buttonText = stringResource(R.string.create_instance),
            modifier = modifier
                .padding(horizontal = 2.dp)
        )
    }
}

@Composable
fun TimeInstanceEntryBody(
    uiState: TimeInstanceUiState,
    onValueChange: (TimeInstanceDetails) -> Unit,
    onSaveClick: () -> Unit,
    buttonText: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Spacer(
            modifier = modifier.padding(4.dp)
        )
        Text(
            text = stringResource(R.string.time_instance_entry_title),
            fontSize = 20.sp,

            modifier = modifier
                .align( Alignment.CenterHorizontally )
        )
        Spacer(
            modifier = modifier.padding(4.dp)
        )
        TimeInstanceEntryForm(
            timeInstance = uiState.timeInstanceDetails,
            onValueChange = onValueChange,
            modifier = modifier
        )
        Button(
            onClick = onSaveClick,
            enabled = uiState.isEntryValid,
            shape = MaterialTheme.shapes.small,
            modifier = modifier
                .padding(horizontal = 40.dp)
                .align( Alignment.CenterHorizontally )
        ) {
            Text(
                text = buttonText
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeInstanceEntryForm(
    timeInstance: TimeInstanceDetails,
    onValueChange: (TimeInstanceDetails) -> Unit = {},
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = null)
    val selectedDateMillis = datePickerState.selectedDateMillis
    val selectedDateString = selectedDateMillis?.let {
        convertMillisToDate(it.toString())
    } ?: convertMillisToDate(timeInstance.date)

    LaunchedEffect(datePickerState.selectedDateMillis) {
        if (selectedDateMillis != null) {
            onValueChange(timeInstance.copy(date = selectedDateMillis.toString()))
        }
    }

    Column(
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selectedDateString,
            onValueChange = { },
            label = { Text(text = stringResource(id = R.string.request_date)) },
            enabled = enabled,
            readOnly = true,
            singleLine = true,
            trailingIcon = {
                IconButton(
                    onClick = { showDatePicker = !showDatePicker }
                ) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = ""
                    )
                }
            },
            modifier = modifier.fillMaxWidth()
        )
        if (showDatePicker) {
            Popup(
                onDismissRequest = { showDatePicker = false },
                alignment = Alignment.TopStart
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = 64.dp)
                        .shadow(elevation = 4.dp)
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(16.dp)
                ) {
                    DatePicker(
                        state = datePickerState,
                        showModeToggle = false,
                    )
                }
            }
        }
        OutlinedTextField(
            value = timeInstance.time,
            onValueChange = { onValueChange(timeInstance.copy(time = it)) },
            label = { Text(text = stringResource(id = R.string.request_amount_of_time)) },
            enabled = enabled,
            singleLine = true,
            modifier = modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TimeInstanceEntryFormPreview() {
    TimeInstanceEntryForm(
        timeInstance =  TimeInstanceDetails(0,0, "1761863753000", "3")
    )
}

@Preview(showBackground = true)
@Composable
fun TimeInstanceEntryBodyPreview() {
    TimeInstanceEntryBody(
        uiState = TimeInstanceUiState(),
        buttonText = "",
        onSaveClick = {},
        onValueChange = {}

    )
}