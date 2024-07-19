package com.example.weatherapp.Details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.ui.theme.font_unt

@Composable
fun TabButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }) {
        ClickableText(
            text = AnnotatedString(text), onClick = { onClick() }, style = TextStyle(
                fontSize = 18.sp,
                fontFamily = font_unt,
                color = if (isSelected) Color.White else Color.LightGray,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            )
        )
        Spacer(modifier = Modifier.height(5.dp))
        if (isSelected) {
            Icon(
                imageVector = Icons.Default.Circle,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(6.dp)
            )
        } else {
            Spacer(modifier = Modifier.size(6.dp))
        }
    }
}