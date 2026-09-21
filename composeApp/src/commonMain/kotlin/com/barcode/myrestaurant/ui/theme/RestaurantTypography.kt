package com.barcode.myrestaurant.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow

object RestaurantFonts {
    val Default   = FontFamily.Default
    val Serif     = FontFamily.Serif
    val SansSerif = FontFamily.SansSerif
    val Monospace = FontFamily.Monospace
    val Cursive   = FontFamily.Cursive
}

@Composable
fun RestText(
    text: String,
    modifier: Modifier = Modifier,
    fontFamily: FontFamily = RestaurantFonts.Default,
    color: Color = RestaurantColors.OnBackground,
    fontSize: TextUnit = 14.sp,
    fontWeight: FontWeight = FontWeight.Normal,
    fontStyle: FontStyle = FontStyle.Normal,
    textAlign: TextAlign = TextAlign.Start,
    lineHeight: TextUnit = TextUnit.Unspecified,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
) {
    Text(
        text = text,
        modifier = modifier,
        style = TextStyle(
            fontFamily = fontFamily,
            color = color,
            fontSize = fontSize,
            fontWeight = fontWeight,
            fontStyle = fontStyle,
            textAlign = textAlign,
            lineHeight = lineHeight,
        ),
        maxLines = maxLines,
        overflow = overflow,
    )
}

// Conveniencia: estilos pre-definidos para la app
@Composable
fun RestHeadline(
    text: String,
    modifier: Modifier = Modifier,
    fontFamily: FontFamily = RestaurantFonts.Default,
    color: Color = RestaurantColors.OnBackground,
) = RestText(
    text = text,
    modifier = modifier,
    fontFamily = fontFamily,
    color = color,
    fontSize = 24.sp,
    fontWeight = FontWeight.Bold,
)

@Composable
fun RestTitle(
    text: String,
    modifier: Modifier = Modifier,
    fontFamily: FontFamily = RestaurantFonts.Default,
    color: Color = RestaurantColors.OnBackground,
) = RestText(
    text = text,
    modifier = modifier,
    fontFamily = fontFamily,
    color = color,
    fontSize = 18.sp,
    fontWeight = FontWeight.SemiBold,
)

@Composable
fun RestBody(
    text: String,
    modifier: Modifier = Modifier,
    fontFamily: FontFamily = RestaurantFonts.Default,
    color: Color = RestaurantColors.OnSurfaceVariant,
) = RestText(
    text = text,
    modifier = modifier,
    fontFamily = fontFamily,
    color = color,
    fontSize = 14.sp,
    fontWeight = FontWeight.Normal,
)

@Composable
fun RestCaption(
    text: String,
    modifier: Modifier = Modifier,
    fontFamily: FontFamily = RestaurantFonts.Default,
    color: Color = RestaurantColors.Placeholder,
) = RestText(
    text = text,
    modifier = modifier,
    fontFamily = fontFamily,
    color = color,
    fontSize = 12.sp,
    fontWeight = FontWeight.Normal,
)