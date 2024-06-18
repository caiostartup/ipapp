@file:OptIn(ExperimentalFoundationApi::class)

package com.hyqoo.ipapp.ui.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
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
import com.hyqoo.ipapp.ui.viewmodel.IpDataSearchViewModel

data class HomeState(
    val windowSizeClass: WindowSizeClass,
)

@Composable
fun MainScreen (
    windowSizeClass: WindowSizeClass,
    ipDataSearchViewModel: IpDataSearchViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {

    val homeScreenUiState by ipDataSearchViewModel.state.collectAsStateWithLifecycle()
    val ipDataUiState by ipDataSearchViewModel.ipDataUiState.collectAsStateWithLifecycle()
    val searchQuery by ipDataSearchViewModel.searchQuery.collectAsStateWithLifecycle(initialValue = "")

    Surface(modifier = modifier) {
        HomeScreen(
            searchQuery = searchQuery,
            ipData = ipDataUiState,
            handleSearch = ipDataSearchViewModel::handleSearch,
            handleTextChange = ipDataSearchViewModel::handleTextChange
        )
//            HomeContent(
//                ipData = ipDataUiState,
//                modifier = Modifier.padding(16.dp)
//            )
    }
//    HomeScreenError("Teste")
//    when (val uiState = homeScreenUiState) {
//        is HomeScreenUiState.Loading -> HomeScreenLoading()
//        is HomeScreenUiState.Error -> HomeScreenError(errorMessage = uiState.errorMessage)
//        is HomeScreenUiState.Ready ->
//            HomeScreen(
//                searchQuery = searchQuery,
//                ipData = ipDataUiState,
//                handleSearch = ipDataSearchViewModel::handleSearch,
//                handleTextChange = ipDataSearchViewModel::handleTextChange,
//                windowSizeClass = windowSizeClass
//            )
//    }
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
fun HomeAppBar(
    searchQuery: String,
    handleTextChange: (String) -> Unit,
    handleSearch: () -> Unit,
    modifier: Modifier = Modifier
) {

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(
    searchQuery: String,
    ipData: IpData?,
    handleSearch: () -> Unit,
    handleTextChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    Scaffold(
        content = { contentPadding ->
            Box(modifier = Modifier.fillMaxSize().padding(contentPadding)) {
                DockedSearchBar(
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
                    },
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(16.dp)
                        .fillMaxSize(),
                ){
                    HomeContent(
                        ipData = ipData,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    )
}

@Composable
private fun HomeContent(
    ipData: IpData?,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
//        verticalArrangement = Arrangement.spacedBy(space = 8.dp, alignment = Alignment.Top),
        horizontalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.CenterHorizontally),
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        item {
            Text(
                text = "ip",
                style = MaterialTheme.typography.headlineSmall
            )
        }
        item {
            Text(
                text = ipData?.ip ?: "",
                style = MaterialTheme.typography.bodyMedium
            )

        }
        item {
            Text(
                text = "country",
                style = MaterialTheme.typography.headlineSmall
            )

        }
        item {
            Text(
                text = ipData?.country ?: "",
                style = MaterialTheme.typography.bodyMedium
            )

        }
        item {
            Text(
                text = "countryCode",
                style = MaterialTheme.typography.headlineSmall
            )
        }
        item {
            Text(
                text = ipData?.countryCode ?: "",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        item {
            Text(
                text = "region",
                style = MaterialTheme.typography.headlineSmall
            )
        }
        item {
            Text(
                text = ipData?.region ?: "",
                style = MaterialTheme.typography.bodyMedium
            )
        }


        item {
            Text(
                text = "regionName",
                style = MaterialTheme.typography.headlineSmall
            )
        }
        item {
            Text(
                text = ipData?.regionName ?: "",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        item {
            Text(
                text = "city",
                style = MaterialTheme.typography.headlineSmall
            )
        }
        item {
            Text(
                text = ipData?.city ?: "",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        item {
            Text(
                text = "zip",
                style = MaterialTheme.typography.headlineSmall
            )
        }
        item {
            Text(
                text = ipData?.zip ?: "",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        item {
            Text(
                text = "lat",
                style = MaterialTheme.typography.headlineSmall
            )
        }
        item {
            Text(
                text = ipData?.lat.toString() ?: "",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        item {
            Text(
                text = "lon",
                style = MaterialTheme.typography.headlineSmall
            )
        }
        item {
            Text(
                text = ipData?.lon.toString() ?: "",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        item {
            Text(
                text = "timezone",
                style = MaterialTheme.typography.headlineSmall
            )
        }
        item {
            Text(
                text = ipData?.timezone ?: "",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        item {
            Text(
                text = "isp",
                style = MaterialTheme.typography.headlineSmall
            )
        }
        item {
            Text(
                text = ipData?.isp ?: "",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        item {
            Text(
                text = "org",
                style = MaterialTheme.typography.headlineSmall
            )
        }
        item {
            Text(
                text = ipData?.org ?: "",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        item {
            Text(
                text = "as",
                style = MaterialTheme.typography.headlineSmall
            )
        }
        item {
            Text(
                text = ipData?.autonomousSystem ?: "",
                style = MaterialTheme.typography.bodyMedium
            )
        }

    }

}

@Composable
private fun RowContent(title: String, content: String?) {
    Column(
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
