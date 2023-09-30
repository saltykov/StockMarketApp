package com.example.stockmarketapp.presentation.company_listing

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockmarketapp.domain.usecases.GetCompaniesUseCase
import com.example.stockmarketapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyListingViewModel @Inject constructor(
    private val getCompaniesUseCase: GetCompaniesUseCase
) : ViewModel() {

    var state by mutableStateOf(CompanyListingState())

    private var searchJob: Job? = null

    init {
        getCompanies()
    }
    fun onEvent(event: CompanyListingEvents) {
        when (event) {
            is CompanyListingEvents.Refresh -> {
                getCompanies(fetchFromRemote = true)
            }
            is CompanyListingEvents.onSearchQueryChange -> {
                state = state.copy(searchQuery = event.query)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    getCompanies()
                }
            }
        }
    }

    private fun getCompanies(
        query: String = state.searchQuery.lowercase(),
        fetchFromRemote: Boolean = false
    ) {
        viewModelScope.launch {
            getCompaniesUseCase(fetchFromRemote, query)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let { listings ->
                                state = state.copy(companies = listings)
                            }
                        }

                        is Resource.Error -> {
                            Unit //todo handle errors
                        }

                        is Resource.Loading -> {
                            state = state.copy(isLoading = true)
                        }
                    }
                }
        }
    }
}