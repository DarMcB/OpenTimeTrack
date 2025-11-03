package com.example.opentimetrack.ui.time

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.opentimetrack.R
import com.example.opentimetrack.ui.AppTopBar
import com.example.opentimetrack.ui.AppViewModelProvider
import com.example.opentimetrack.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

object TimeInstanceUpdateDestination : NavigationDestination {
    override val route = "time_instance_update"
    override val titleRes = R.string.time_instance_title
    const val timeInstanceIdArg = "timeInstanceId"
    val routeArg = "$route/{$timeInstanceIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeInstanceUpdateScreen(
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TimeInstanceUpdateViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            AppTopBar(
                title = stringResource(R.string.time_instance_update_title),
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        },
        modifier = modifier
    ) { innerPadding ->
        TimeInstanceUpdateBody(
            onSaveClick = { coroutineScope.launch {
                viewModel.updateTimeInstance()
            }},
            onDeleteClick = { coroutineScope.launch {
                viewModel.deleteTimeInstance()
            }},
            modifier = modifier.padding(innerPadding),
            uiState = viewModel.uiState,
            onValueChange = viewModel::updateUiState
        )
    }
}

@Composable
fun TimeInstanceUpdateBody(
    onSaveClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
    uiState: TimeInstanceUiState,
    onValueChange: (TimeInstanceDetails) -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.End
        ) {
            Button(
                onClick = onDeleteClick,
                enabled = true,
                shape = MaterialTheme.shapes.small,
                modifier = modifier.padding()
            ) {
                Text(
                    text = stringResource(R.string.delete_instance)
                )
            }
        }
        TimeInstanceEntryBody(
            uiState = uiState,
            onValueChange = onValueChange,
            onSaveClick = onSaveClick,
            buttonText = stringResource(R.string.update_instance),
            modifier = Modifier
                .padding()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TimeInstanceUpdateBodyPreview() {
    TimeInstanceEntryForm(
        timeInstance =  TimeInstanceDetails(0,0, "1751927498270", "43")
    )
}