package com.example.investiq.presentation.company_favorites

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.investiq.domain.model.CompanyFavItem
import com.example.investiq.presentation.company_list.CompanyListingViewmodel
import com.example.investiq.presentation.screens.Screen


@Composable
fun FavouriteCompanyScreen(
    navigator: NavController,
    companyFavoriteViewModel: CompanyFavoriteViewModel= hiltViewModel()
){

    val state = companyFavoriteViewModel.state


    Box(modifier= Modifier
        .fillMaxSize()
    ) {

        Column(modifier=Modifier.fillMaxSize()) {


            Box(modifier = Modifier.fillMaxWidth(),
            ){
                Text("Favorites",
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(start = 12.dp, top = 12.dp, bottom = 12.dp),
                    fontSize = 25.sp)
            }


            if(state.data.isNotEmpty()){

                LazyColumn(modifier = Modifier.fillMaxSize()) {

                    itemsIndexed(state.data){ index: Int, item: CompanyFavItem ->

                        Log.d("CompanyFavScreen", "Fetched data: $item")

                        CompanyFavoriteItem(company=item, onClick = { navigator.navigate(Screen.FavoritesScreen.route+"/${item.symbol}")  },
                            viewModel = companyFavoriteViewModel)
                    }
                }

            }
            else{

                Box(modifier= Modifier
                    .padding(12.dp)
                    .fillMaxSize()
                    .padding(10.dp)
                    , contentAlignment = Alignment.Center
                ){
                    Text(text = "Nothing in Favorites Yet!!!",
                        color = Color.Black,
                        fontSize = 15.sp
                    )
                }

            }


        }

    }

}