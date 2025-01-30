package com.example.investiq

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.investiq.presentation.company_info.CompanyInfoScreen
import com.example.investiq.presentation.company_list.CompanyListScreen
import com.example.investiq.presentation.bottomNav.BottomBarTab
import com.example.investiq.presentation.bottomNav.CustomBottomNavigation
import com.example.investiq.presentation.company_favorites.FavouriteCompanyScreen
import com.example.investiq.presentation.screens.Screen
import com.example.investiq.ui.theme.InvestIQTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InvestIQTheme {

//                SetBarColor(color = Color.Transparent)

                val hazeState = remember { HazeState() }

                val navController = rememberNavController()


                Scaffold(modifier = Modifier.fillMaxSize()
                    .background(color = Color.White)
                    .statusBarsPadding(),
                    bottomBar = {

                        val navBackStackEntry by navController.currentBackStackEntryAsState()

                        // Get the current route
                        val currentDestination = navBackStackEntry?.destination?.route

                        Log.d("DES",currentDestination ?:"Null")
                        if (currentDestination.equals("Home") || currentDestination.equals("Favourites")) {
                            CustomBottomNavigation(hazeState,navController)
                        }
                    }) { padding ->

                    Column( Modifier
                        .haze(
                            hazeState,
                            backgroundColor = MaterialTheme.colorScheme.background,
                            tint = if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.S_V2) Color.Black.copy(alpha = .1f) else Color.Black.copy(alpha = 1f) ,
                            blurRadius = 30.dp,
                        )
                        .fillMaxSize()) {


                        NavHost(navController = navController, startDestination = BottomBarTab.Home.name ) {

                            composable(Screen.FavoritesScreen.route+"/{symbol}"){ CompanyInfoScreen() }
                            composable(BottomBarTab.Home.name){ CompanyListScreen(navController) }
                            composable(BottomBarTab.Favourites.name){ FavouriteCompanyScreen(navController) }

                        }
                    }

                }

            }
        }
    }

    @Composable
    fun SetBarColor(color: Color){
        val systemUiController = rememberSystemUiController()
        SideEffect {
            systemUiController.setSystemBarsColor(
                color = color
            )
        }
    }
}


