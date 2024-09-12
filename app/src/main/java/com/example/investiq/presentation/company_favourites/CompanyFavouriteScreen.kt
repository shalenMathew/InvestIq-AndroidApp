package com.example.investiq.presentation.company_favourites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview


@Preview
@Composable
fun FavouriteCompanyScreen(){
    Box(modifier = Modifier.fillMaxSize().background(color = Color.White), contentAlignment = Alignment.Center){
        Text(text = "Favorite Screen")
    }
}