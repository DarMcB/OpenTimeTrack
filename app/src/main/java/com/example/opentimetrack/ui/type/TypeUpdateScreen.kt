package com.example.opentimetrack.ui.type

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.opentimetrack.data.entity.Type
import com.example.opentimetrack.ui.AppTopBar
import com.example.opentimetrack.ui.AppViewModelProvider
import com.example.opentimetrack.ui.navigation.NavigationDestination
import com.example.opentimetrack.ui.theme.OpenTimeTrackTheme
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
    typeId: Int,
) {
    val viewModel: TypeUpdateViewModel = viewModel(factory = TypeUpdateViewModel.factory(typeId))
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(typeId) {
        viewModel.load(typeId)
    }

    Card(
        modifier = modifier.padding()
    ) {
        TypeUpdateBody(
            typeUiState = viewModel.typeUiState,
            onValueChange = viewModel::updateUiState,
            onSaveClick = { coroutineScope.launch { viewModel.updateType() } },
            onDeleteClick = { coroutineScope.launch { viewModel.deleteType() } },
            title = stringResource(R.string.type_update_title),
            modifier = modifier.padding(horizontal = 2.dp),
        )
    }
}

@Composable
fun TypeUpdateBody(
    typeUiState: TypeUiState,
    onValueChange: (Type) -> Unit,
    onSaveClick: () -> Unit,
    onDeleteClick: () -> Unit,
    title: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Spacer(
            modifier = modifier.padding(2.dp)
        )
        Text(
            text = title,
            fontSize = 20.sp,
            modifier = modifier
                .align( Alignment.CenterHorizontally )
        )
        TypeEntryForm(
            type = typeUiState.typeDetails,
            onValueChange = onValueChange,
            modifier = modifier.padding()
        )
        Row (
            modifier = modifier.fillMaxWidth()
        ) {
            Button(
                onClick = onSaveClick,
                enabled = typeUiState.isEntryValid,
                shape = MaterialTheme.shapes.small,
                modifier = modifier
                    .padding()

            ) {
                Text(
                    text = stringResource(R.string.update_instance)
                )
            }
            Spacer(
                modifier = modifier.weight(1f)
            )
            Button(
                onClick = onDeleteClick,
                enabled = true,
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
fun TypeUpdateBodyPreview() {
    OpenTimeTrackTheme {
        TypeUpdateBody(
            onSaveClick = {},
            onValueChange = {},
            title = stringResource(R.string.type_entry_title),
            typeUiState = TypeUiState(),
            onDeleteClick = {}
        )
    }
}