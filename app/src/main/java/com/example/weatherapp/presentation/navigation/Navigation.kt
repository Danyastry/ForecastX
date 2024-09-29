package com.example.weatherapp.presentation.navigation

import MainScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.weatherapp.presentation.auth.AuthState
import com.example.weatherapp.presentation.forecast.CurrentForecast
import com.example.weatherapp.presentation.forecast.DayAfterTomorrow
import com.example.weatherapp.presentation.home.HomeScreen
import com.example.weatherapp.presentation.auth.LoginScreen
import com.example.weatherapp.presentation.auth.SignUpScreen
import com.example.weatherapp.presentation.forecast.TomorrowForecast
import com.example.weatherapp.presentation.auth.AuthViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()
    val authState = authViewModel.authState.observeAsState()
    NavHost(navController = navController, startDestination = if (authState.value is AuthState.Authenticated) "home" else "main") {
        composable("main"){
            MainScreen(navController = navController, authViewModel = AuthViewModel())
        }
        composable("signup"){
            SignUpScreen(navController = navController)
        }
        composable("login"){
            LoginScreen(navController = navController)
        }
        composable("home") {
            HomeScreen(navController = navController, authViewModel = AuthViewModel())
        }
        composable("currentForecast/{city}/{country}", arguments = listOf(
            navArgument("city") {
                NavType.StringType
            }
        )) {
            val city = it.arguments?.getString("city") ?: ""
            CurrentForecast(navController = navController, city = city)
        }
        composable("tomorrowForecast/{city}/{country}", arguments = listOf(
            navArgument("city"){
                NavType.StringType
            }
        )){
            val city = it.arguments?.getString("city") ?: ""
            TomorrowForecast(navController = navController, city = city)
        }
        composable("dayAfterTomorrow/{city}/{country}", arguments = listOf(
            navArgument("city"){
                NavType.StringType
            }
        )){
            val city = it.arguments?.getString("city") ?: ""
            DayAfterTomorrow(navController = navController, city = city)
        }
    }
}