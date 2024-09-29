package com.example.weatherapp.presentation.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.weatherapp.presentation.search.CitySearch
import com.example.weatherapp.presentation.auth.AuthState
import com.example.weatherapp.domain.model.BaseModel
import com.example.weatherapp.presentation.auth.AuthViewModel
import com.example.weatherapp.ui.theme.font_basic
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = viewModel(),
    authViewModel: AuthViewModel
) {
    val authState = authViewModel.authState.observeAsState()

    val locations by viewModel.locations.collectAsState()

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val (city, setCity) = remember {
        mutableStateOf("")
    }

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Unauthenticated -> navController.navigate("login")
            else -> Unit
        }

    }

    LaunchedEffect(city) {
        delay(500)
        if (city.isNotEmpty()) {
            viewModel.getLocation(city)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 64.dp)
            .pointerInput(Unit) {
                detectTapGestures {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "ForecastX",
            color = Color.White,
            fontSize = 25.sp,
            fontFamily = font_basic,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(15.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            TextField(modifier = Modifier.fillMaxWidth(), value = city,
                onValueChange = {
                    setCity(it)
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    cursorColor = Color.White
                ), textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 16.sp,
                    fontFamily = font_basic
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }
                ),
                placeholder = {
                    Text(text = "Enter your city", color = Color.LightGray)
                },
                singleLine = true
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        AnimatedVisibility(
            visible = locations is BaseModel.Success,
            enter = fadeIn() + scaleIn(),
            exit = fadeOut() + scaleOut()
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                when (val data = locations) {
                    is BaseModel.Success -> {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(1),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(data.data) { location ->
                                CitySearch(location = location, navController = navController)
                            }
                        }
                    }

                    else -> {}
                }
            }
            AnimatedVisibility(
                visible = locations is BaseModel.Loading,
                enter = fadeIn() + scaleIn(),
                exit = fadeOut() + scaleOut()
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        }
        TextButton(onClick = { authViewModel.signOut() }, modifier = Modifier.offset(y = 650.dp)) {
            Text(text = "Sign Out", color = Color.White, fontSize = 25.sp, fontFamily = font_basic)

        }
    }
}



