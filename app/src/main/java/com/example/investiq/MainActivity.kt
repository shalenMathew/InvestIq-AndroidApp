package com.example.investiq

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.investiq.presentation.NavGraphs
import com.example.investiq.presentation.company_list.bottomNav.BottomBarTab
import com.example.investiq.presentation.company_list.bottomNav.BottomBarTabs
import com.example.investiq.presentation.company_list.bottomNav.CustomBottomNavigation
import com.example.investiq.ui.theme.InvestIQTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.DestinationsNavHost
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
                        if (currentDestination.equals("Home")) {
                            CustomBottomNavigation(hazeState)
                        }
                    }) { padding ->


                    Column(  Modifier
                        .haze(
                            hazeState,
                            backgroundColor = MaterialTheme.colorScheme.background,
                            tint = Color.Black.copy(alpha = .2f),
                            blurRadius = 30.dp,
                        )
                        .fillMaxSize()) {

                        // will remove this nav
                        DestinationsNavHost(navGraph = NavGraphs.root)



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


