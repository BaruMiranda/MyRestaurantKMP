package com.barcode.myrestaurant.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.barcode.myrestaurant.ui.theme.RestaurantColors
import com.barcode.myrestaurant.ui.theme.RestText

@Composable
fun RestFilledButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    containerColor: Color = RestaurantColors.Primary,
    contentColor: Color = RestaurantColors.OnPrimary,
    disabledContainerColor: Color = RestaurantColors.Border,
    disabledContentColor: Color = RestaurantColors.Placeholder,
    shape: Shape = RoundedCornerShape(12.dp),
    height: Dp = 52.dp,
    fontFamily: FontFamily = FontFamily.Default,
) {
    Button(
        onClick = { if (!isLoading) onClick() },
        modifier = modifier.height(height),
        enabled = enabled && !isLoading,
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = disabledContainerColor,
            disabledContentColor = disabledContentColor,
        ),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 0.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            when {
                isLoading -> CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = contentColor,
                    strokeWidth = 2.dp,
                )
                else -> {
                    leadingIcon?.let {
                        Icon(imageVector = it, contentDescription = null, modifier = Modifier.size(20.dp))
                        Spacer(Modifier.width(8.dp))
                    }
                    RestText(
                        text = text,
                        fontFamily = fontFamily,
                        color = if (enabled) contentColor else disabledContentColor,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                    trailingIcon?.let {
                        Spacer(Modifier.width(8.dp))
                        Icon(imageVector = it, contentDescription = null, modifier = Modifier.size(20.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun RestOutlinedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    borderColor: Color = RestaurantColors.Primary,
    contentColor: Color = RestaurantColors.Primary,
    disabledBorderColor: Color = RestaurantColors.Border,
    disabledContentColor: Color = RestaurantColors.Placeholder,
    shape: Shape = RoundedCornerShape(12.dp),
    height: Dp = 52.dp,
    strokeWidth: Dp = 1.5.dp,
    fontFamily: FontFamily = FontFamily.Default,
) {
    OutlinedButton(
        onClick = { if (!isLoading) onClick() },
        modifier = modifier.height(height),
        enabled = enabled && !isLoading,
        shape = shape,
        border = BorderStroke(
            width = strokeWidth,
            color = if (enabled) borderColor else disabledBorderColor,
        ),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = contentColor,
            disabledContentColor = disabledContentColor,
        ),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 0.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            when {
                isLoading -> CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = contentColor,
                    strokeWidth = 2.dp,
                )
                else -> {
                    leadingIcon?.let {
                        Icon(imageVector = it, contentDescription = null, modifier = Modifier.size(20.dp))
                        Spacer(Modifier.width(8.dp))
                    }
                    RestText(
                        text = text,
                        fontFamily = fontFamily,
                        color = if (enabled) contentColor else disabledContentColor,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                    trailingIcon?.let {
                        Spacer(Modifier.width(8.dp))
                        Icon(imageVector = it, contentDescription = null, modifier = Modifier.size(20.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun RestTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: ImageVector? = null,
    contentColor: Color = RestaurantColors.Primary,
    fontFamily: FontFamily = FontFamily.Default,
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.textButtonColors(contentColor = contentColor),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            leadingIcon?.let {
                Icon(imageVector = it, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(6.dp))
            }
            RestText(
                text = text,
                fontFamily = fontFamily,
                color = if (enabled) contentColor else RestaurantColors.Placeholder,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
            )
        }
    }
}