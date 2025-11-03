package com.example.opentimetrack.ui.type

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.opentimetrack.data.entity.Type
import com.example.opentimetrack.ui.AppTopBar
import com.example.opentimetrack.ui.AppViewModelProvider
import com.example.opentimetrack.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

object TypeUpdateDestination : NavigationDestination {
    override val route = "type_update"
    override val titleRes = R.string.type_update_title
    const val typeIdArg = "typeId"
    val routeArg = "$route/{$typeIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TypeUpdateScreen(
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TypeUpdateViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            AppTopBar(
                title = stringResource(TypeUpdateDestination.titleRes),
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        TypeUpdateBody(
            onSaveClick = { coroutineScope.launch { viewModel.updateType() }},
            onDeleteClick = { coroutineScope.launch { viewModel.deleteType() }},
            modifier = modifier.padding( innerPadding ),
            typeUiState = viewModel.typeUiState,
            onValueChange = viewModel::updateUiState
        )
    }
}

@Composable
fun TypeUpdateBody(
    onSaveClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
    typeUiState: TypeUiState,
    onValueChange: (Type) -> Unit
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
        TypeEntryBody(
            typeUiState = typeUiState,
            onValueChange = onValueChange,
            onSaveClick = onSaveClick,
            buttonText = stringResource(R.string.update_instance),
            modifier = Modifier
                .padding()
        )

    }
}


@Preview
@Composable
fun TypeUpdateBodyPreview() {
    TypeUpdateBody(
        onSaveClick = {},
        onDeleteClick = {},
        onValueChange = {},
        typeUiState = TypeUiState()
    )
}