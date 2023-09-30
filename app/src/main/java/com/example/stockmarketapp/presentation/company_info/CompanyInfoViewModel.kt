package com.example.stockmarketapp.presentation.company_info

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockmarketapp.domain.model.CompanyInfo
import com.example.stockmarketapp.domain.model.DailyInfo
import com.example.stockmarketapp.domain.usecases.GetCompanyInfoUseCase
import com.example.stockmarketapp.domain.usecases.GetDailyInfoUseCase
import com.example.stockmarketapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyInfoViewModel @Inject constructor(
    private val getDailyInfoUseCase: GetDailyInfoUseCase,
    private val getCompanyInfoUseCase: GetCompanyInfoUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(CompanyInfoState())

    init {
        viewModelScope.launch {
            val symbol = savedStateHandle.get<String>("symbol") ?: return@launch
            state = state.copy(isLoading = true)
            val companyInfoResult = async { getCompanyInfo(symbol) }
            val dailyInfoResult = async { getDailyInfo(symbol) }
            //CompanyInfo
            state = when (val result = companyInfoResult.await()) {
                is Resource.Success -> {
                    state.copy(
                        companyInfo = result.data,
                        isLoading = false,
                        error = null
                    )
                }
                is Resource.Error -> {
                    state.copy(
                        error = result.message,
                        isLoading = false
                    )

                }

                is Resource.Loading -> {
                    state.copy(isLoading = true)
                }
            }
            //DailyInfo
            state = when (val result = dailyInfoResult.await()) {
                is Resource.Success -> {
                    state.copy(
                        dailyInfo = result.data ?: emptyList(),
                        isLoading = false,
                        error = null
                    )
                }

                is Resource.Error -> {
                    state.copy(
                        error = result.message,
                        isLoading = false
                    )

                }

                is Resource.Loading -> {
                    state.copy(isLoading = true)
                }
            }
        }
    }

    private suspend fun getCompanyInfo(symbol: String): Resource<CompanyInfo> {
        return getCompanyInfoUseCase(symbol)
    }

    private suspend fun getDailyInfo(symbol: String): Resource<List<DailyInfo>> {
        return getDailyInfoUseCase(symbol)
    }


}