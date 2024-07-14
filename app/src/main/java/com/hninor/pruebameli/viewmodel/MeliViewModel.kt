package com.hninor.pruebameli.viewmodel

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hninor.pruebameli.api.ApiResponseStatus
import com.hninor.pruebameli.api.entry.Results
import com.hninor.pruebameli.api.entry.SearchResult
import com.hninor.pruebameli.data.MeliRemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeliViewModel @Inject constructor(private val repository: MeliRemoteRepository) :
    ViewModel() {

    val snackbarHostState = SnackbarHostState()
    var searchResults by mutableStateOf<ApiResponseStatus<SearchResult>>(
        ApiResponseStatus.Success(
            SearchResult()
        )
    )
        private set

    var searchQuery by mutableStateOf("")
        private set


    lateinit var searchResultSelected: Results


    private fun loadResults(query: String) {
        viewModelScope.launch {
            searchResults = ApiResponseStatus.Loading()
            searchResults = repository.fetchResults(query)
        }
    }


    fun onSearchQueryChange(newQuery: String) {
        searchQuery = newQuery
    }

    fun onSearch() {
        loadResults(searchQuery)
    }


}