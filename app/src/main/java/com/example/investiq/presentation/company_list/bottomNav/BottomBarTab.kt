package com.example.investiq.presentation.company_list.bottomNav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Star
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarTab ( val name:String,val icon:ImageVector,val color:Color){
    data object Home:BottomBarTab("Home", Icons.Rounded.Home,Color(0xFFFFA574))
    data object Favourites:BottomBarTab("Favourites",Icons.Rounded.Star,Color(0xFFFFA574))
}

val tabs= listOf(
    BottomBarTab.Home,
    BottomBarTab.Favourites
)