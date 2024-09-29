package com.example.weatherapp.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weatherapp.data.model.Hour
import com.example.weatherapp.ui.theme.font_basic
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HourlyForecastItem(hour: Hour) {
    val timeInMills = SimpleDateFormat("yyyy-MM-dd HH:mm").parse(hour.time).time
    val formattedTime = SimpleDateFormat("H a", Locale.getDefault()).format(Date(timeInMills))

    Column(
        modifier = Modifier
            .size(100.dp, 140.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primary),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = formattedTime, color = Color.White, fontFamily = font_basic)
        Spacer(modifier = Modifier.height(8.dp))
        AsyncImage(
            modifier = Modifier.size(65.dp),
            model = "https:${hour.condition.icon}".replace("64x64", "128x128"),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "${hour.temp_c}°",
            color = Color.White,
            fontSize = 16.sp,
            fontFamily = font_basic
        )
    }
}