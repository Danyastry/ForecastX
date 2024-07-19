package com.example.weatherapp.Details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.weatherapp.DailyForecast.Location
import com.example.weatherapp.ui.theme.font_basic

@Composable
fun CitySearch(location: Location, navController: NavController) {
    Card(
        modifier = Modifier
            .clickable {
                navController.navigate("currentForecast/${location.name}/${location.country}")
            }
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Row(
            Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = location.name,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontFamily = font_basic,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = location.country,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontFamily = font_basic
                )
            }
        }
    }
}