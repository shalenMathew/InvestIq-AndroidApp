package com.example.investiq.presentation.company_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.investiq.ui.theme.PurpleGrey40
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination(start = true)
fun CompanyListScreen(
    navigator: DestinationsNavigator,
    viewmodel:CompanyListingViewmodel = hiltViewModel(),
){

    val swipeFreshState = rememberSwipeRefreshState(isRefreshing = viewmodel.state.isRefreshing)

    val state = viewmodel.state

    Column(modifier=Modifier.fillMaxSize()) {

        OutlinedTextField(
            value = state.searchQuery, // initial value
            onValueChange ={
                // will be triggered when user input some string
                viewmodel.onEvent(CompanyListingEvent.OnSearchQueryChanged(it))
            },
            modifier = Modifier
                .padding(bottom=16.dp,start=16.dp,end=16.dp)
                .fillMaxWidth(),
            placeholder = {
                Text(text = "Search...")
            },
            maxLines = 1,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedPlaceholderColor = Color.Black,
                focusedBorderColor=Color.Black,
                unfocusedBorderColor= PurpleGrey40,
                unfocusedPlaceholderColor = PurpleGrey40,
                focusedTextColor = Color.Black,
            ))

        SwipeRefresh(state = swipeFreshState,
            onRefresh = {
                viewmodel.getCompanyListing()
            }) {

            LazyColumn(modifier=Modifier.fillMaxSize()) {

                items(state.companyList){
                    CompanyItem(company = it, onClick = {})
                }

            }

        }

    }




}