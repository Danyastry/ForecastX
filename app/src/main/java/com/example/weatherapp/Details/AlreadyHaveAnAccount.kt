package com.example.weatherapp.Details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.weatherapp.ui.theme.font_unt

@Composable
fun AlreadyHaveAnAccount(onTap: () -> Unit = {}) {
    Row() {
        Text(
            text = "Already have an account? ",
            fontSize = 18.sp,
            fontFamily = font_unt,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Text(
            text = "Sign In",
            fontSize = 18.sp,
            fontFamily = font_unt,
            color = Color.White,
            modifier = Modifier.clickable {onTap()}
        )

    }

}