package com.example.stockappcompose

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.stockappcompose.ui.layout.common.BackButton
import com.example.stockappcompose.ui.layout.common.CommonMiddleLabel
import com.example.stockappcompose.ui.layout.screen.StockDetailScreen
import com.example.stockappcompose.ui.layout.screen.StockListScreen
import com.example.stockappcompose.ui.theme.AppBackground
import com.example.stockappcompose.viewmodel.StockDetailViewModel

@SuppressLint("RestrictedApi", "StateFlowValueCalledInComposition")
@Composable
fun StockApp() {
    val navController = rememberNavController()
    val backStack = navController.currentBackStack.collectAsState().value
    val hasBackStack = backStack.size > 2
    val screenName = Screen.fromRoute(backStack.lastOrNull()?.destination?.route).screenName
    val onNavigateToScreen: (Route) -> Unit = { route ->
        navController.navigate(route.value)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppTopBar(
                screenName = screenName,
                hasBackStack = hasBackStack,
                onClickBackButton = {
                    navController.popBackStack()
                }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .background(AppBackground)
            ) {
                StockAppNavHost(
                    navController = navController,
                    onNavigateToScreen = onNavigateToScreen,
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppTopBar(
    screenName: String,
    hasBackStack: Boolean,
    onClickBackButton: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            CommonMiddleLabel(text = screenName)
        },
        navigationIcon = {
            if (hasBackStack) {
                BackButton(onClick = onClickBackButton)
            }
        },
    )
}

@Composable
private fun StockAppNavHost(
    navController: NavHostController,
    onNavigateToScreen: (Route) -> Unit,
) {
    NavHost(navController = navController, startDestination = "StockList") {
        composable(route = "StockList") {
            StockListScreen(onNavigateToScreen = onNavigateToScreen)
        }
        composable(
            route = "StockDetail/{amount}/?comment={comment}/{createDate}/?imageUri={imageUri}",
            arguments = listOf(
                navArgument("amount") { type = NavType.IntType },
                navArgument("comment") {
                    type = NavType.StringType
                    nullable = true
                },
                navArgument("createDate") { type = NavType.StringType },
                navArgument("imageUri") {
                    type = NavType.StringType
                    nullable = true
                }
            ),
        ) { backStackEntry ->
            val amount = backStackEntry.arguments?.getInt("amount")
            val comment = backStackEntry.arguments?.getString("comment")
            val createDateString = backStackEntry.arguments?.getString("createDate")
            val imageUri = backStackEntry.arguments?.getString("imageUri")
            if (amount != null && createDateString != null) {
                val stock = Stock(comment, amount, createDateString.toLocalDateTime(Constants.DATETIME_FORMAT_YYYYMMDDHHMMSSSSS), imageUri)
                StockDetailScreen(
                    stockDetailViewModel = viewModel(
                        factory = viewModelFactory {
                            initializer {
                                StockDetailViewModel(stock)
                            }
                        }
                    )
                )
            }
        }
    }
}