package com.barcode.myrestaurant.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.barcode.myrestaurant.ui.theme.RestaurantColors
import com.barcode.myrestaurant.ui.theme.RestCaption

@Composable
fun RestTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "",
    placeholder: String = "",
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    onTrailingIconClick: (() -> Unit)? = null,
    errorMessage: String? = null,
    supportingText: String? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    shape: Shape = RoundedCornerShape(12.dp),
    fontFamily: FontFamily = FontFamily.Default,
    containerColor: Color = RestaurantColors.Surface,
    focusedBorderColor: Color = RestaurantColors.BorderFocus,
    unfocusedBorderColor: Color = RestaurantColors.Border,
) {
    val isError = errorMessage != null

    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            readOnly = readOnly,
            label = if (label.isNotEmpty()) ({ Text(label, fontFamily = fontFamily) }) else null,
            placeholder = if (placeholder.isNotEmpty()) ({
                Text(placeholder, fontFamily = fontFamily, color = RestaurantColors.Placeholder)
            }) else null,
            leadingIcon = leadingIcon?.let {
                { Icon(imageVector = it, contentDescription = null, tint = RestaurantColors.OnSurfaceVariant) }
            },
            trailingIcon = trailingIcon?.let {
                {
                    if (onTrailingIconClick != null) {
                        IconButton(onClick = onTrailingIconClick) {
                            Icon(imageVector = it, contentDescription = null, tint = RestaurantColors.OnSurfaceVariant)
                        }
                    } else {
                        Icon(imageVector = it, contentDescription = null, tint = RestaurantColors.OnSurfaceVariant)
                    }
                }
            },
            isError = isError,
            singleLine = singleLine,
            maxLines = maxLines,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
            keyboardActions = keyboardActions,
            shape = shape,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (isError) RestaurantColors.Error else focusedBorderColor,
                unfocusedBorderColor = if (isError) RestaurantColors.Error else unfocusedBorderColor,
                focusedLabelColor = if (isError) RestaurantColors.Error else focusedBorderColor,
                unfocusedLabelColor = RestaurantColors.OnSurfaceVariant,
                focusedContainerColor = containerColor,
                unfocusedContainerColor = containerColor,
                disabledContainerColor = RestaurantColors.SurfaceVariant,
                cursorColor = focusedBorderColor,
            ),
        )

        AnimatedVisibility(visible = isError || supportingText != null) {
            RestCaption(
                text = errorMessage ?: supportingText ?: "",
                modifier = Modifier.padding(start = 12.dp, top = 4.dp),
                color = if (isError) RestaurantColors.Error else RestaurantColors.Placeholder,
            )
        }
    }
}

@Composable
fun RestPasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Contraseña",
    placeholder: String = "Ingresa tu contraseña",
    errorMessage: String? = null,
    enabled: Boolean = true,
    imeAction: ImeAction = ImeAction.Done,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    shape: Shape = RoundedCornerShape(12.dp),
    fontFamily: FontFamily = FontFamily.Default,
) {
    var passwordVisible by remember { mutableStateOf(false) }

    val isError = errorMessage != null

    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            label = { Text(label, fontFamily = fontFamily) },
            placeholder = {
                Text(placeholder, fontFamily = fontFamily, color = RestaurantColors.Placeholder)
            },
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña",
                        tint = RestaurantColors.OnSurfaceVariant,
                    )
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            isError = isError,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = imeAction),
            keyboardActions = keyboardActions,
            shape = shape,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (isError) RestaurantColors.Error else RestaurantColors.BorderFocus,
                unfocusedBorderColor = if (isError) RestaurantColors.Error else RestaurantColors.Border,
                focusedLabelColor = if (isError) RestaurantColors.Error else RestaurantColors.BorderFocus,
                unfocusedLabelColor = RestaurantColors.OnSurfaceVariant,
                cursorColor = RestaurantColors.BorderFocus,
            ),
        )

        AnimatedVisibility(visible = isError) {
            RestCaption(
                text = errorMessage ?: "",
                modifier = Modifier.padding(start = 12.dp, top = 4.dp),
                color = RestaurantColors.Error,
            )
        }
    }
}