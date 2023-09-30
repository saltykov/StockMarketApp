package com.example.stockmarketapp.presentation.company_info

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination
fun CompanyInfoScreen(
    symbol: String,
    viewModel: CompanyInfoViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val scrollState = rememberScrollState()
    if (state.error == null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
            state.companyInfo?.let { companyInfo ->
                Text(
                    text = companyInfo.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = companyInfo.symbol,
                    fontStyle = FontStyle.Italic,
                    fontSize = 14.sp,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                Divider(
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Industry: ${companyInfo.industry}",
                    fontSize = 14.sp,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Country: ${companyInfo.country}",
                    fontSize = 14.sp,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                Divider(
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))


                Text(
                    text = if (companyInfo.description != "None") companyInfo.description
                    else "No description about this company",
                    fontSize = 12.sp,
                    modifier = Modifier.fillMaxWidth()
                )
                if (state.dailyInfo.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Divider(
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Market Summary")
                    Spacer(modifier = Modifier.height(32.dp))
                    StockChart(
                        dailyInfo = state.dailyInfo,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .align(CenterHorizontally)
                    )
                }
            }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Center
    ) {
        if (state.isLoading) {
            CircularProgressIndicator()
        } else if (state.error != null) {
            Text(
                text = state.error,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

