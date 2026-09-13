package com.example.opentimetrack.ui.settings

import android.content.Context
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.opentimetrack.R
import com.example.opentimetrack.ui.AppTopBar
import com.example.opentimetrack.ui.AppViewModelProvider
import com.example.opentimetrack.ui.navigation.NavigationDestination

object SettingsScreenDestination : NavigationDestination {
    override val route = "settings_screen"
    override val titleRes = R.string.settings_title
    const val typeIdArg = "typeId"
    val routeArg = "$route/{$typeIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val context = LocalContext.current

    Scaffold(
        modifier = modifier,
        topBar = {
            AppTopBar(
                title = stringResource(R.string.settings_title),
                canNavigateBack = true,
                canNavigateCustom = false,
                navigateUp = navigateBack,
                navigateToCustom = {}
            )
        }
    ) { innerPadding ->
        SettingsBody(
            contentPadding = innerPadding,
            export = { viewModel.exportAsCsv(context) },
            enabled = true //TODO update this when functional
        )
    }
}

@Composable
fun SettingsBody(
    contentPadding: PaddingValues = PaddingValues(),
    export: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    TextButton(
        enabled = enabled,
        onClick = export,
        modifier = modifier.padding(contentPadding),
        colors = ButtonDefaults.buttonColors()

    ) {
        Text(
            text = stringResource(R.string.exportAsCSV)
        )
    }
}