package com.example.opentimetrack.ui.type

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.opentimetrack.data.entity.Type
import com.example.opentimetrack.ui.theme.OpenTimeTrackTheme
import com.example.opentimetrack.R
import com.example.opentimetrack.ui.AppViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.opentimetrack.ui.AppTopBar
import com.example.opentimetrack.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

object TypeEntryDestination : NavigationDestination {
    override val route = "type_entry"
    override val titleRes = R.string.type_entry_title
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TypeEntryScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    canNavigateBack: Boolean = true,
    viewModel: TypeEntryViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            AppTopBar(
                title = stringResource(R.string.type_entry_title),
                canNavigateBack = canNavigateBack,
                navigateUp = onNavigateUp
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        TypeEntryBody(
            typeUiState = viewModel.typeUiState,
            onValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveItem()
                    navigateBack
                }
            },
            buttonText = stringResource(R.string.create_instance),
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding()
                )
                .fillMaxWidth()
        )
    }
}

@Composable
fun TypeEntryBody(
    typeUiState: TypeUiState,
    onValueChange: (Type) -> Unit,
    onSaveClick: () -> Unit,
    buttonText: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        TypeEntryForm(
            type = typeUiState.typeDetails,
            onValueChange = onValueChange,
            modifier = modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            enabled = typeUiState.isEntryValid,
            shape = MaterialTheme.shapes.small,
            modifier = modifier.fillMaxWidth()
        ) {
            Text(
                text = buttonText
            )
        }
    }
}

@Composable
fun TypeEntryForm(
    type: Type,
    onValueChange: (Type) -> Unit = {},
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        OutlinedTextField(
            value = type.name,
            onValueChange = { onValueChange(type.copy(name = it)) },
            label = { Text(text = stringResource(id = R.string.request_type_name)) },
            enabled = enabled,
            singleLine = true,
            modifier = modifier.fillMaxWidth()
        )
        if (enabled) {
            Text(
                text = stringResource(R.string.required_field),
                modifier = Modifier.padding()
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun TypeEntryFormPreview() {
    OpenTimeTrackTheme {
        TypeEntryForm(
            Type(0, ""),
            onValueChange = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TypeEntryBodyPreview() {
    OpenTimeTrackTheme {
        TypeEntryBody(
            onSaveClick = {},
            onValueChange = {},
            buttonText = stringResource(R.string.create_instance),
            typeUiState = TypeUiState()
        )
    }
}