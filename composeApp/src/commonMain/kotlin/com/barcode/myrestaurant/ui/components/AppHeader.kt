package com.barcode.myrestaurant.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.barcode.myrestaurant.ui.theme.RestaurantColors
import com.barcode.myrestaurant.ui.theme.RestText

@Composable
fun AppHeader(
    title: String,
    subtitle: String? = null,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(RestaurantColors.AuthBackground),
    ) {
        // Decorative circles
        Box(
            Modifier
                .size(110.dp)
                .offset(x = 300.dp, y = (-35).dp)
                .clip(CircleShape)
                .background(RestaurantColors.AuthAccent.copy(alpha = 0.75f))
        )
        Box(
            Modifier
                .size(18.dp)
                .offset(x = 270.dp, y = 65.dp)
                .clip(CircleShape)
                .background(RestaurantColors.AuthAccent.copy(alpha = 0.45f))
        )
        Box(
            Modifier
                .size(12.dp)
                .offset(x = 20.dp, y = 12.dp)
                .clip(CircleShape)
                .background(RestaurantColors.AuthAccent.copy(alpha = 0.4f))
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(start = 24.dp, end = 24.dp, top = 20.dp, bottom = 24.dp),
        ) {
            RestText(
                text = title,
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )
            if (subtitle != null) {
                Spacer(Modifier.height(4.dp))
                RestText(
                    text = subtitle,
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 13.sp,
                )
            }
        }
    }
}
