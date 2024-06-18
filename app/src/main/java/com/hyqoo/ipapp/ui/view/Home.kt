@file:OptIn(ExperimentalFoundationApi::class)

package com.hyqoo.ipapp.ui.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.core.layout.WindowSizeClass
import com.hyqoo.ipapp.R
import com.hyqoo.ipapp.model.IpData
import com.hyqoo.ipapp.ui.radialGradientScrim
import com.hyqoo.ipapp.ui.theme.IpAppTheme
import com.hyqoo.ipapp.ui.tooling.DevicePreviews
import com.hyqoo.ipapp.ui.viewmodel.HomeScreenUiState
import com.hyqoo.ipapp.ui.viewmodel.IpDataSearchViewModel

data class HomeState(
    val windowSizeClass: WindowSizeClass,
)

@Composable
fun MainScreen(
    windowSizeClass: WindowSizeClass,
    ipDataSearchViewModel: IpDataSearchViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {

    val homeScreenUiState by ipDataSearchViewModel.state.collectAsStateWithLifecycle()
    val ipDataUiState by ipDataSearchViewModel.ipDataUiState.collectAsStateWithLifecycle()
    val searchQuery by ipDataSearchViewModel.searchQuery.collectAsStateWithLifecycle(initialValue = "")

    when (val uiState = homeScreenUiState) {
        is HomeScreenUiState.Loading -> HomeScreenLoading()
        is HomeScreenUiState.Error -> HomeScreenError(errorMessage = uiState.errorMessage)
        is HomeScreenUiState.Ready ->
            HomeScreen(
                searchQuery = searchQuery,
                ipData = ipDataUiState,
                handleSearch = ipDataSearchViewModel::handleSearch,
                handleTextChange = ipDataSearchViewModel::handleTextChange,
                windowSizeClass = windowSizeClass
            )
    }
}

@Composable
private fun HomeScreenLoading(modifier: Modifier = Modifier) {
    Surface(modifier.fillMaxSize()) {
        Box {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
private fun HomeScreenError(errorMessage: String, modifier: Modifier = Modifier) {
    Surface(modifier = modifier) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize(),
        ) {
            Text(
                text = stringResource(id = R.string.an_error_has_occurred),
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = errorMessage,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenErrorPreview() {
    IpAppTheme {
        HomeScreenError(errorMessage = "Teste")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeAppBar(
    searchQuery: String,
    handleTextChange: (String) -> Unit,
    handleSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.End,
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .padding(end = 16.dp, top = 8.dp, bottom = 8.dp)
    ) {
        SearchBar(
            query = searchQuery,
            onQueryChange = handleTextChange,
            placeholder = {
                Text(stringResource(id = R.string.search_for_a_podcast))
            },
            onSearch = {},
            active = true,
            onActiveChange = {},
            trailingIcon = {
                IconButton(
                    onClick = handleSearch
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                }
            }
        ) { }
    }
}

@Composable
private fun HomeScreenBackground(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .radialGradientScrim(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f))
        )
        content()
    }
}

@Composable
private fun HomeScreen(
    searchQuery: String,
    ipData: IpData?,
    handleSearch: () -> Unit,
    handleTextChange: (String) -> Unit,
    windowSizeClass: WindowSizeClass,
    modifier: Modifier = Modifier
) {

    val snackbarHostState = remember { SnackbarHostState() }

    HomeScreenBackground(
        modifier = modifier.windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        Scaffold(
            topBar = {
                HomeAppBar(
                    searchQuery = searchQuery,
                    handleSearch = handleSearch,
                    handleTextChange = handleTextChange,
                    modifier = Modifier.fillMaxWidth(),
                )
            },
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
            containerColor = Color.Transparent
        ) { contentPadding ->
            HomeContent(
                showGrid = true,
                ipData = ipData,
                modifier = Modifier.padding(contentPadding)
            )
        }
    }
}

@Composable
private fun HomeContent(
    showGrid: Boolean,
    ipData: IpData?,
    modifier: Modifier = Modifier
) {
    Surface(modifier = Modifier.padding(16.dp).fillMaxSize()) {

        Row {
            RowContent(title = "ip", content = ipData?.ip)
            RowContent(title = "country", content = ipData?.country)
            RowContent(title = "countryCode", content = ipData?.countryCode)
            RowContent(title = "region", content = ipData?.region)
            RowContent(title = "regionName", content = ipData?.regionName)
            RowContent(title = "city", content = ipData?.city)
            RowContent(title = "zip", content = ipData?.zip)
            RowContent(title = "lat", content = ipData?.lat.toString())
            RowContent(title = "lon", content = ipData?.lon.toString())
            RowContent(title = "timezone", content = ipData?.timezone)
            RowContent(title = "isp", content = ipData?.isp)
            RowContent(title = "org", content = ipData?.org)
            RowContent(title = "as", content = ipData?.autonomousSystem)
        }
    }


//    if (showGrid) HomeContentGrid() else HomeContentColumn()
}

@Composable
private fun RowContent(title: String, content: String?){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            text = title,
            modifier = Modifier.padding(8.dp)
        )
        Text(
            text = content ?: "",
            modifier = Modifier.padding(8.dp)
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun HomeAppBarPreview() {
    IpAppTheme {
        HomeAppBar(
            searchQuery = "127.0.0.1",
            handleTextChange = {},
            handleSearch = {}
        )
    }
}

private val CompactWindowSizeClass = WindowSizeClass.compute(360f, 780f)

@DevicePreviews
@Composable
private fun PreviewHome() {
    IpAppTheme {
        val homeState = HomeState(
            windowSizeClass = CompactWindowSizeClass
        )
//        HomeScreen(
//            searchQuery = "127.0.0.1"
//        )
    }
}
