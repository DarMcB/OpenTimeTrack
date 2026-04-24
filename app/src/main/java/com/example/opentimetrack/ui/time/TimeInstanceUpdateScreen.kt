package com.example.opentimetrack.ui.time

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.opentimetrack.R
import com.example.opentimetrack.ui.AppTopBar
import com.example.opentimetrack.ui.AppViewModelProvider
import com.example.opentimetrack.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeInstanceUpdateScreen(
    modifier: Modifier = Modifier,
    timeInstanceId: Int,
) {
    val viewModel: TimeInstanceUpdateViewModel = viewModel(factory = TimeInstanceUpdateViewModel.factory(timeInstanceId))
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(timeInstanceId) {
        viewModel.load(timeInstanceId)
    }

    Card(
        modifier = modifier.padding()
    ) {
        TimeInstanceUpdateBody(
            onSaveClick = { coroutineScope.launch {
                viewModel.updateTimeInstance()
            }},
            onDeleteClick = { coroutineScope.launch {
                viewModel.deleteTimeInstance()
            }},
            modifier = modifier.padding(),
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
        modifier = modifier
    ) {
        Spacer(
            modifier = modifier.padding(4.dp)
        )
        Text(
            text = stringResource(R.string.time_instance_update_title),
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
            modifier = modifier.padding(bottom = 4.dp)
        )
        Row(
            modifier = modifier.fillMaxWidth()
        ) {
            Button(
                onClick = onSaveClick,
                enabled = uiState.isEntryValid,
                shape = MaterialTheme.shapes.small,
                modifier = modifier
                    .padding()
            ) {
                Text(
                    text = stringResource(R.string.update_instance)
                )
            }
            Spacer(modifier.weight(1f))
            Button(
                onClick = onDeleteClick,
                enabled = uiState.isEntryValid,
                shape = MaterialTheme.shapes.small,
                modifier = modifier
                    .padding()
            ) {
                Text(
                    text = stringResource(R.string.delete_instance)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TimeInstanceUpdateBodyPreview() {
    TimeInstanceUpdateBody(
        onSaveClick = {},
        onDeleteClick = {},
        onValueChange = {},
        uiState = TimeInstanceUiState()
    )
}