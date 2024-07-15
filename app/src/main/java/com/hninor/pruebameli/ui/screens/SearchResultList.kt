package com.hninor.pruebameli.ui.screens

// for a `var` variable also add

// or just

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.rememberTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hninor.pruebameli.R
import com.hninor.pruebameli.api.ApiResponseStatus
import com.hninor.pruebameli.api.entry.Results
import com.hninor.pruebameli.api.entry.SearchResult
import com.hninor.pruebameli.viewmodel.MeliViewModel


@Composable
fun SearchScreen(viewModel: MeliViewModel, onResultClick: (result: Results) -> Unit) {


    SearchScreen(
        searchQuery = viewModel.searchQuery,
        apiResponseStatus = viewModel.searchResults,
        onSearchQueryChange = { viewModel.onSearchQueryChange(it) },
        onResultClick = onResultClick,
        onSearch = { viewModel.onSearch() }
    )
}


@Composable
fun SearchScreen(
    searchQuery: String,
    apiResponseStatus: ApiResponseStatus<SearchResult>,
    onSearchQueryChange: (String) -> Unit,
    onResultClick: (result: Results) -> Unit,
    onSearch: () -> Unit
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    SearchBar(
        query = searchQuery,
        onQueryChange = onSearchQueryChange,
        onSearch = {
            keyboardController?.hide()
            onSearch()
        },
        placeholder = {
            Text(text = stringResource(id = R.string.buscar_productos))
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = null
            )
        },
        trailingIcon = {
            if (searchQuery.isNotEmpty()) {
                IconButton(onClick = {
                    onSearchQueryChange("")
                    onSearch()
                }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = "Clear search"
                    )
                }
            }
        },
        content = {
            SearchResultList(apiResponseStatus = apiResponseStatus, onResultClick = onResultClick)
        },
        active = true,
        onActiveChange = {},
        tonalElevation = 0.dp
    )
}

@Composable
fun SearchResultList(
    apiResponseStatus: ApiResponseStatus<SearchResult>,
    onResultClick: (result: Results) -> Unit
) {

    when (apiResponseStatus) {
        is ApiResponseStatus.Error -> ErrorMessage(text = apiResponseStatus.message)
        is ApiResponseStatus.Loading -> Loading()
        is ApiResponseStatus.Success -> {
            SearchResultList(
                results = apiResponseStatus.data.results,
                onResultClick = onResultClick
            )
        }
    }


}

@Composable
fun SearchResultList(results: List<Results>, onResultClick: (result: Results) -> Unit) {
    if (results.isEmpty()) {
        ListEmptyState()
    } else {
        LazyColumn {
            items(results) { result ->
                CharacterItem(result = result, onClick = onResultClick)
            }
        }
    }

}

@Composable
private fun CharacterItem(result: Results, onClick: (results: Results) -> Unit) {
    ListItem(
        modifier = Modifier.clickable { onClick(result) },
        headlineContent = {
            // Mission name
            Text(text = "${result.title}")
        },
        supportingContent = {
            // Site
            Text(text = result.condition ?: "")
        },
        leadingContent = {
            // Mission patch
            AsyncImage(
                modifier = Modifier.size(68.dp, 68.dp),
                model = result.thumbnail,
                placeholder = painterResource(R.drawable.ic_placeholder),
                error = painterResource(R.drawable.ic_placeholder),
                contentDescription = "Mission patch"
            )
        }
    )
}

@Composable
private fun LoadingItem() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorMessage(text: String) {
    val infiniteTransition = rememberInfiniteTransition(label = "infinite transition")
    val animatedColor by infiniteTransition.animateColor(
        initialValue = Color(0xFF60DDAD),
        targetValue = Color(0xFF4285F4),
        animationSpec = infiniteRepeatable(tween(1000), RepeatMode.Reverse),
        label = "color"
    )
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        BasicText(
            text = text,
            color = { animatedColor },
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
private fun Loading() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}


@Composable
fun ListEmptyState(
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(id = R.string.sin_resultados),
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = stringResource(id = R.string.intenta_otra_busqueda),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
