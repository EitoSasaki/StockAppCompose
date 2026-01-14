package com.example.stockappcompose

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.stockappcompose.ui.layout.common.CommonImageButton
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
    val onPopToScreen: (Route?) -> Unit = { route ->
        if (route == null) {
            navController.popBackStack()
        } else {
            navController.popBackStack(route.value, inclusive = false)
        }
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
                    onPopToScreen = onPopToScreen,
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
                CommonImageButton(
                    image = Icons.Default.ArrowBack,
                    onClick = onClickBackButton
                )
            }
        },
    )
}

@Composable
private fun StockAppNavHost(
    navController: NavHostController,
    onNavigateToScreen: (Route) -> Unit,
    onPopToScreen: (Route?) -> Unit,
) {
    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    }
    NavHost(navController = navController, startDestination = "StockList") {
        composable(route = "StockList") {
            StockListScreen(
                stockListViewModel = viewModel(viewModelStoreOwner),
                onNavigateToScreen = onNavigateToScreen
            )
        }
        composable(
            route = "StockDetail/{id}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType },
            ),
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id")
            if (id != null) {
                val viewModel = viewModel<StockDetailViewModel>()
                viewModel.setId(id)
                StockDetailScreen(
                    stockDetailViewModel = viewModel,
                    onPopToScreen = onPopToScreen,
                )
            }
        }
    }
}