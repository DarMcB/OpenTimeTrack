package com.example.opentimetrack.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.AutoMirrored.Filled
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.opentimetrack.R
import com.example.opentimetrack.ui.navigation.TimeNavHost
import com.example.opentimetrack.ui.theme.OpenTimeTrackTheme

@Composable
fun App(navController: NavHostController = rememberNavController()) {
    TimeNavHost(
        navController = navController
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    canNavigateBack: Boolean,
    canNavigateCustom: Boolean,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {},
    navigateToCustom: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = { Text( text = title ) },
        scrollBehavior = scrollBehavior,
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        },
        actions = {
            if (canNavigateCustom) {
                IconButton(onClick = navigateToCustom) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = ""
                    )
                }
            }
        }
    )
}

@Composable
fun AppBottomBar(
    leftButtonName: String,
    rightButtonName: String,
    navigateToTimeInstance: (Int) -> Unit,
    navigateToStatsScreen: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    BottomAppBar(
        modifier = modifier.fillMaxWidth()
    ) {
        IconButton(
            onClick = { navigateToTimeInstance },
            modifier = modifier.padding(horizontal = 60.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.List,
                contentDescription = "",
                modifier = modifier.fillMaxSize()
            )
        }
        Spacer(Modifier.weight(1f))
        IconButton(
            onClick = { navigateToStatsScreen },
            modifier = modifier.padding(horizontal = 60.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "",
                modifier = modifier.fillMaxSize()
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun AppTopBarPreview() {
    OpenTimeTrackTheme {
        AppTopBar(
            title = "App Title",
            canNavigateBack = false,
            canNavigateCustom = true
        )
    }
}

@Preview
@Composable
fun AppBottomBarPreview() {
    OpenTimeTrackTheme {
        AppBottomBar(
            leftButtonName = "List",
            rightButtonName = "Stats",
            navigateToStatsScreen = {},
            navigateToTimeInstance = {}
        )
    }
}
