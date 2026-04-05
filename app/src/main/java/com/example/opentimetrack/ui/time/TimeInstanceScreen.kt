package com.example.opentimetrack.ui.time

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.opentimetrack.R
import com.example.opentimetrack.data.entity.TimeInstance
import com.example.opentimetrack.data.entity.Type
import com.example.opentimetrack.ui.AppTopBar
import com.example.opentimetrack.ui.AppViewModelProvider
import com.example.opentimetrack.ui.navigation.NavigationDestination
import com.example.opentimetrack.ui.theme.OpenTimeTrackTheme
import kotlinx.coroutines.launch

object TimeInstanceDestination : NavigationDestination {
    override val route = "time_instance"
    override val titleRes = R.string.time_instance_title
    const val typeIdArg = "typeId"
    val routeArg = "$route/{$typeIdArg}"
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeInstanceScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TimeInstanceViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val timeInstanceUiState by viewModel.timeInstanceUiState.collectAsState()
    val typeUiState = viewModel.typeUiState
    val typeId = viewModel.typeId
    val coroutineScope = rememberCoroutineScope()

    var showTimeInstanceEntryScreen by remember { mutableStateOf(false) }
    var showTimeInstanceUpdateScreen by remember { mutableStateOf(false) }

    var timeInstanceId: Int = 0

    Scaffold(
        modifier = modifier,
        topBar = {
            AppTopBar(
                title = typeUiState.typeDetails.name,
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showTimeInstanceEntryScreen = true },
                shape = MaterialTheme.shapes.medium,
                modifier = modifier.padding()
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = ""
                )
            }
        },
    ) { innerPadding ->
        if (showTimeInstanceEntryScreen) {
            Dialog(
                onDismissRequest = { showTimeInstanceEntryScreen = false }
            ) {
                TimeInstanceEntryScreen(
                    navigateBack = {},
                    onNavigateUp = {},
                )
            }
        }
        if (showTimeInstanceUpdateScreen) {
                Dialog(
                    onDismissRequest = {
                        showTimeInstanceUpdateScreen = false
                    }
                ) {
                    TimeInstanceUpdateScreen(
                        timeInstanceId = timeInstanceId,
                    )
                }
        }
        TimeInstanceBody(
            timeInstanceList = timeInstanceUiState.timeInstanceList,
            onTimeInstanceClick = {
                showTimeInstanceUpdateScreen = true
                timeInstanceId = it
            },
            modifier = modifier.fillMaxSize(),
            contentPadding = innerPadding
        )
    }
}

@Composable
fun TimeInstanceBody(
    timeInstanceList: List<TimeInstance>,
    onTimeInstanceClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues()
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (timeInstanceList.isEmpty()) {
            Text(
                text = stringResource(R.string.no_time_instances),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = modifier.padding(contentPadding)
            )
        } else {
            TimeInstanceList(
                timeInstanceList = timeInstanceList,
                onTimeInstanceClick = { onTimeInstanceClick(it.id) },
                contentPadding = contentPadding,
                modifier = modifier.padding()
            )
        }
    }
}

@Composable
fun TimeInstanceList(
    timeInstanceList: List<TimeInstance>,
    onTimeInstanceClick: (TimeInstance) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding
    ) {
        items(timeInstanceList, key = { it.id }) { timeInstance ->
            TimeInstanceItem(timeInstance = timeInstance,
                modifier = Modifier
                    .padding()
                    .clickable { onTimeInstanceClick(timeInstance) })
        }
    }
}

@Composable
fun TimeInstanceItem(
    timeInstance: TimeInstance,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, 4.dp)
                .width(4.dp)
        ) {
            Row(modifier = modifier) {
                Text(
                    text = convertMillisToDate(timeInstance.date.toString()),
                    fontSize = 32.sp
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = timeInstance.time.toString(),
                    fontSize = 32.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TimeInstanceBodyPreview() {
    TimeInstanceBody(
        onTimeInstanceClick = {},
        timeInstanceList = listOf(
        )
    )
}

@Preview(showBackground = true)
@Composable
fun TimeInstanceListPreview() {
    OpenTimeTrackTheme {
        TimeInstanceList(
            timeInstanceList = listOf(
                TimeInstanceDetails(1, 1, "", "").toTimeInstance(),
                TimeInstanceDetails(2, 1, "", "").toTimeInstance()
            ),
            onTimeInstanceClick = {},
            contentPadding = PaddingValues()
        )
    }
}

@Preview
@Composable
fun TimeInstanceItemPreview() {
    OpenTimeTrackTheme {
        Type(1, "Immersion")

        TimeInstanceItem(
            TimeInstanceDetails(
                id = 1,
                typeId = 1,
                date = "",
                time = ""
            ).toTimeInstance()
        )
    }
}