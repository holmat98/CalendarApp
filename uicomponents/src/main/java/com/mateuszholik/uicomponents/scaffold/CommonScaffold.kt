package com.mateuszholik.uicomponents.scaffold

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonScaffold(
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    title: @Composable () -> Unit = {},
    topAppBarScrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
    snackbarHost: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    colors: CommonScaffoldColors = CommonScaffoldDefaults.colors(),
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        modifier = modifier.nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                scrollBehavior = topAppBarScrollBehavior,
                navigationIcon = navigationIcon,
                title = title,
                actions = actions,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colors.topBarContainerColor,
                    navigationIconContentColor = colors.topBarContentColor,
                    titleContentColor = colors.topBarContentColor,
                    actionIconContentColor = colors.topBarContentColor,
                )
            )
        },
        snackbarHost = snackbarHost,
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = floatingActionButtonPosition,
        content = content,
        containerColor = colors.containerColor,
        contentColor = colors.contentColor,
    )
}

@Immutable
data class CommonScaffoldColors internal constructor(
    val containerColor: Color,
    val contentColor: Color,
    val topBarContainerColor: Color,
    val topBarContentColor: Color,
)

object CommonScaffoldDefaults {

    @Composable
    @ReadOnlyComposable
    fun colors(
        containerColor: Color = MaterialTheme.colorScheme.surface,
        contentColor: Color = MaterialTheme.colorScheme.onSurface,
        topBarContainerColor: Color = MaterialTheme.colorScheme.surface,
        topBarContentColor: Color = MaterialTheme.colorScheme.onSurface,
    ): CommonScaffoldColors =
        CommonScaffoldColors(
            containerColor = containerColor,
            contentColor = contentColor,
            topBarContainerColor = topBarContainerColor,
            topBarContentColor = topBarContentColor,
        )
}
