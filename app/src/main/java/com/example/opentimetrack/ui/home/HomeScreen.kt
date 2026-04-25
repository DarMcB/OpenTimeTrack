package com.example.opentimetrack.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.opentimetrack.data.entity.Type
import com.example.opentimetrack.ui.AppViewModelProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.opentimetrack.R
import com.example.opentimetrack.ui.AppBottomBar
import com.example.opentimetrack.ui.AppTopBar
import com.example.opentimetrack.ui.navigation.NavigationDestination
import com.example.opentimetrack.ui.theme.OpenTimeTrackTheme
import com.example.opentimetrack.ui.type.TypeEntryScreen
import com.example.opentimetrack.ui.type.TypeUpdateScreen
import kotlin.Unit

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToTypeEntry: () -> Unit,
    navigateToTypeUpdate: (Int) -> Unit,
    navigateToTimeInstance: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val homeUiState by viewModel.homeUiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    var showTypeEntryScreen by remember { mutableStateOf(false) }
    var showTypeUpdateScreen by remember { mutableStateOf(false) }

    var typeId by remember { mutableIntStateOf(0) }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AppTopBar(
                title = stringResource(R.string.app_name),
                canNavigateBack = false,
                canNavigateCustom = false,
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            AppBottomBar(
                leftButtonName = "list",
                rightButtonName = "stats",
                navigateToTimeInstance = { /* Already on this screen */ },
                navigateToStatsScreen = { /*TODO*/ },
                modifier = modifier
            )

        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showTypeEntryScreen = true },
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
        if (showTypeEntryScreen) {
            Dialog(
                onDismissRequest = { showTypeEntryScreen = false }
            ) {
                TypeEntryScreen(
                    navigateBack = {},
                    onNavigateUp = {},
                )
            }
        }
        if (showTypeUpdateScreen) {
            Dialog(
                onDismissRequest = { showTypeUpdateScreen = false }
            ) {
                TypeUpdateScreen(
                    onNavigateUp = {},
                    typeId = typeId
                )
            }
        }
        HomeBody(
            typeList = homeUiState.typeList,
            onTypeClick = { navigateToTimeInstance(it) },
            onTypeLongClick = {
                showTypeUpdateScreen = true
                typeId = it
            },
            modifier = modifier.fillMaxSize(),
            contentPadding = innerPadding
        )
    }
}

@Composable
fun HomeBody(
    typeList: List<Type>,
    onTypeClick: (Int) -> Unit,
    onTypeLongClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(4.dp)
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        if (typeList.isEmpty()) {
            Text(
                text = stringResource(R.string.no_types),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(contentPadding)
            )
        } else {
            TypeList(
                typeList = typeList,
                onTypeClick = { onTypeClick(it.id) },
                onMenuClick = { onTypeLongClick(it.id) },
                contentPadding = contentPadding,
                modifier = modifier.padding()
            )
        }
    }
}

@Composable
fun TypeList(
    typeList: List<Type>,
    onTypeClick: (Type) -> Unit,
    onMenuClick: (Type) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding
    ) {
        items(
            items = typeList,
            key = { it.id }
        ) { type ->
            TypeItem(
                type = type,
                onTypeClick = onTypeClick,
                onMenuClick = onMenuClick,
                modifier = Modifier
                    .padding()
            )
        }
    }
}

@Composable
fun TypeItem(
    type: Type,
    onTypeClick: (Type) -> Unit,
    onMenuClick: (Type) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(50),
        modifier = modifier.clickable {
            onTypeClick(type)
        }
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = type.name,
                textAlign = TextAlign.Start,
                fontSize = 32.sp,
                modifier = modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            )
            /*TODO "Total Time Value" here (text)
            Text(
                text = ,
                textAlign = TextAlign.Center,
                fontSize = 32.sp,
                modifier = modifier
            )
            */
            IconButton(
                onClick = { onMenuClick(type) },
                modifier = modifier.padding(),
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    ""
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TypeItemPreview() {
    OpenTimeTrackTheme {
        TypeItem(Type(1, "Reading"), onTypeClick = {}, onMenuClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun TypeListPreview() {
    OpenTimeTrackTheme {
        TypeList(
            typeList = listOf(
                Type(0, "Spanish"),
                Type(1, "Study")
            ),
            onTypeClick = {},
            onMenuClick = {},
            contentPadding = PaddingValues(0.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeBodyPreview() {
    OpenTimeTrackTheme {
        HomeBody(
            typeList = listOf(
                Type(1, "Japanese"),
                Type(2, "French"),
                Type(3, "Study")
            ),
            onTypeClick = {},
            onTypeLongClick = {}
        )
    }
}