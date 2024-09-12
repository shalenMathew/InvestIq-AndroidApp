package com.example.investiq.presentation.screens

sealed class Screen (val route:String){
    object HomeScreen:Screen("HomeScreen")
    object FavoritesScreen:Screen("FavoritesScreen")
}