package com.example.weatherapp.Navigation

import MainScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.weatherapp.Models.AuthState
import com.example.weatherapp.Screens.CurrentForecast
import com.example.weatherapp.Screens.DayAfterTomorrow
import com.example.weatherapp.Screens.HomeScreen
import com.example.weatherapp.Screens.LoginScreen
import com.example.weatherapp.Screens.SignUpScreen
import com.example.weatherapp.Screens.TomorrowForecast
import com.example.weatherapp.ViewModel.AuthViewModel

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