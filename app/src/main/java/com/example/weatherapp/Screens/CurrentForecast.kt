package com.example.weatherapp.Screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.AirlineStops
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.BrightnessAuto
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Grain
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.weatherapp.Details.TabButton
import com.example.weatherapp.Details.WeatherForecastHourly
import com.example.weatherapp.Models.BaseModel
import com.example.weatherapp.ViewModel.WeatherViewModel
import com.example.weatherapp.ui.theme.font_basic
import com.example.weatherapp.ui.theme.font_unt

@Composable
fun CurrentForecast(
    navController: NavController,
    viewModel: WeatherViewModel = viewModel(),
    city: String
) {

    val forecast by viewModel.forecast.collectAsState()

    val selectedTap by remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        viewModel.getForecast(city, 1)
    }

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Row(modifier = Modifier.padding(top = 52.dp)) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                modifier = Modifier
                    .size(30.dp)
                    .clickable { navController.navigate("home") },
                tint = Color.White,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(10.dp))
            AnimatedVisibility(visible = forecast is BaseModel.Success) {
                Column {
                    forecast is BaseModel.Success
                    val data = forecast as BaseModel.Success
                    Text(
                        text = data.data.location.name,
                        color = Color.White,
                        fontFamily = font_basic,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = data.data.location.country,
                        color = Color.LightGray,
                        fontSize = 18.sp,
                        fontFamily = font_basic

                    )
                }
            }
            AnimatedVisibility(visible = forecast is BaseModel.Loading) {
                CircularProgressIndicator(color = Color.White)

            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        AnimatedVisibility(visible = forecast is BaseModel.Success) {
            val data = forecast as BaseModel.Success
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "${data.data.current.temp_c.toInt()}°",
                    color = Color.White,
                    fontSize = 80.sp,
                    fontFamily = font_basic,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = data.data.current.condition.text,
                    color = Color.White,
                    fontSize = 25.sp,
                    fontFamily = font_basic
                )
            }
        }
        AnimatedVisibility(visible = forecast is BaseModel.Loading) {
            CircularProgressIndicator(color = Color.White)

        }
        Spacer(modifier = Modifier.height(16.dp))

        AnimatedVisibility(visible = forecast is BaseModel.Success) {
            val data = forecast as BaseModel.Success
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TabButton(
                    text = "Current",
                    isSelected = true,
                    onClick = {}
                )
                TabButton(text = "Tomorrow",
                    isSelected = selectedTap == "tomorrow",
                    onClick = {
                        navController.navigate("tomorrowForecast/${data.data.location.name}/${data.data.location.country}")
                    }
                )
                TabButton(text = "Day After Tomorrow",
                    isSelected = selectedTap == "dayAfterTomorrow",
                    onClick = {
                        navController.navigate("dayAfterTomorrow/${data.data.location.name}/${data.data.location.country}")
                    }
                )
            }
        }
        AnimatedVisibility(visible = forecast is BaseModel.Loading) {
            CircularProgressIndicator(color = Color.White)
        }

        AnimatedVisibility(visible = forecast is BaseModel.Success) {
            val data = forecast as BaseModel.Success
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .padding(vertical = 12.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.primary),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Air,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${data.data.current.wind_mph} mp/h",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontFamily = font_unt,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Wind",
                        color = Color.LightGray,
                        fontSize = 14.sp,
                        fontFamily = font_unt
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    AsyncImage(
                        modifier = Modifier.size(80.dp),
                        model = "https:${data.data.current.condition.icon}".replace(
                            "64x64",
                            "128x128"
                        ),
                        contentDescription = null
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Grain,
                        tint = Color.White,
                        contentDescription = null,
                        modifier = Modifier.size(30.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${data.data.current.humidity}%",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontFamily = font_unt,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Humidity",
                        color = Color.LightGray,
                        fontSize = 14.sp,
                        fontFamily = font_unt
                    )
                }
            }
        }
        AnimatedVisibility(visible = forecast is BaseModel.Loading) {
            CircularProgressIndicator(color = Color.White)

        }
        Spacer(modifier = Modifier.height(16.dp))

        AnimatedVisibility(visible = forecast is BaseModel.Success) {
            val data = forecast as BaseModel.Success
            WeatherForecastHourly(forecast = data.data.forecast.forecastday[0].hour.take(24))
        }
        AnimatedVisibility(visible = forecast is BaseModel.Loading) {
            CircularProgressIndicator(color = Color.White)
        }

        AnimatedVisibility(visible = forecast is BaseModel.Success) {
            val data = forecast as BaseModel.Success
            Row(
                modifier = Modifier
                    .padding(top = 24.dp, bottom = 14.dp)
                    .fillMaxWidth()
                    .height(80.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.primary),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "${data.data.forecast.forecastday[0].day.maxtemp_c}°",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontFamily = font_basic,
                    )
                    Text(text = "Max temp", color = Color.White)
                }
                Icon(
                    modifier = Modifier.size(40.dp),
                    imageVector = Icons.Default.ArrowUpward,
                    tint = Color(0xff2eff8c),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(30.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "${data.data.forecast.forecastday[0].day.mintemp_c}°",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontFamily = font_basic,
                    )
                    Text(text = "Min temp", color = Color.White)
                }
                Icon(
                    modifier = Modifier.size(40.dp),
                    imageVector = Icons.Default.ArrowDownward,
                    tint = Color(0xffff5353),
                    contentDescription = null
                )

            }
        }
        AnimatedVisibility(visible = forecast is BaseModel.Loading) {
            CircularProgressIndicator(color = Color.White)

        }

        AnimatedVisibility(visible = forecast is BaseModel.Success) {
            val data = forecast as BaseModel.Success
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.primary),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "${data.data.current.cloud}%",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontFamily = font_basic,
                    )
                    Text(text = "Cloud", color = Color.White)
                }
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    modifier = Modifier.size(40.dp),
                    imageVector = Icons.Default.Cloud,
                    tint = Color.White,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(30.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "${data.data.current.gust_mph} mp/h",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontFamily = font_basic,
                    )
                    Text(text = "Wind gust", color = Color.White)
                }
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    modifier = Modifier.size(40.dp),
                    imageVector = Icons.Default.AirlineStops,
                    tint = Color.White,
                    contentDescription = null
                )
            }
        }
        AnimatedVisibility(visible = forecast is BaseModel.Loading) {
            CircularProgressIndicator(color = Color.White)
        }
        Spacer(modifier = Modifier.height(16.dp))

        AnimatedVisibility(visible = forecast is BaseModel.Success) {
            val data = forecast as BaseModel.Success
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.primary),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "${data.data.current.heatindex_c}°",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontFamily = font_basic,
                    )
                    Text(text = "Heat Index", color = Color.White)
                }
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    modifier = Modifier.size(40.dp),
                    imageVector = Icons.Default.BrightnessAuto,
                    tint = Color.White,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(30.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "${data.data.forecast.forecastday[0].day.daily_chance_of_rain}%",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontFamily = font_basic,
                    )
                    Text(text = "Chance of rain", color = Color.White)
                }
                Spacer(modifier = Modifier.width(8.dp))
                AsyncImage(
                    modifier = Modifier.size(55.dp),
                    model = "https://cdn.weatherapi.com/weather/64x64/day/176.png",
                    contentDescription = null,
                )
            }
        }
        AnimatedVisibility(visible = forecast is BaseModel.Loading) {
            CircularProgressIndicator(color = Color.White)
        }
    }
}





