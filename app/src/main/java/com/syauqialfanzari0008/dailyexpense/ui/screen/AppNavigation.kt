package com.syauqialfanzari0008.dailyexpense.ui.screen

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.syauqialfanzari0008.dailyexpense.ui.viewmodel.ExpenseViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel: ExpenseViewModel = viewModel()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onAddClick = { navController.navigate("add") },
                onItemClick = { id -> navController.navigate("detail/$id") },
                onRecycleBinClick = { navController.navigate("recycle_bin") },
                viewModel = viewModel
            )
        }
        composable("add") {
            AddExpenseScreen(
                onBack = { navController.popBackStack() },
                viewModel = viewModel
            )
        }
        composable(
            route = "detail/{expenseId}",
            arguments = listOf(navArgument("expenseId") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("expenseId") ?: return@composable
            DetailExpenseScreen(
                expenseId = id,
                onBack = { navController.popBackStack() },
                onEditClick = { navController.navigate("edit/$it") },
                viewModel = viewModel
            )
        }
        composable(
            route = "edit/{expenseId}",
            arguments = listOf(navArgument("expenseId") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("expenseId") ?: return@composable
            EditExpenseScreen(
                expenseId = id,
                onBack = { navController.popBackStack() },
                viewModel = viewModel
            )
        }
        composable("recycle_bin") {
            RecycleBinScreen(
                onBack = { navController.popBackStack() },
                viewModel = viewModel
            )
        }
    }
}