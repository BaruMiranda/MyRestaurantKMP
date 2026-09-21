package com.barcode.myrestaurant.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.barcode.myrestaurant.ui.theme.RestaurantColors
import com.barcode.myrestaurant.ui.theme.RestCaption

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> RestDropdown(
    selectedItem: T?,
    items: List<T>,
    onItemSelected: (T) -> Unit,
    itemLabel: (T) -> String,
    modifier: Modifier = Modifier,
    label: String = "",
    placeholder: String = "Seleccionar...",
    leadingIcon: ImageVector? = null,
    enabled: Boolean = true,
    errorMessage: String? = null,
    shape: Shape = RoundedCornerShape(12.dp),
    fontFamily: FontFamily = FontFamily.Default,
    focusedBorderColor: Color = RestaurantColors.BorderFocus,
    unfocusedBorderColor: Color = RestaurantColors.Border,
) {
    var expanded by remember { mutableStateOf(false) }
    val arrowRotation by animateFloatAsState(targetValue = if (expanded) 180f else 0f)
    val isError = errorMessage != null

    Column(modifier = modifier) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { if (enabled) expanded = it },
        ) {
            OutlinedTextField(
                value = selectedItem?.let { itemLabel(it) } ?: "",
                onValueChange = {},
                readOnly = true,
                enabled = enabled,
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(MenuAnchorType.PrimaryNotEditable),
                label = if (label.isNotEmpty()) ({ Text(label, fontFamily = fontFamily) }) else null,
                placeholder = {
                    Text(placeholder, fontFamily = fontFamily, color = RestaurantColors.Placeholder)
                },
                leadingIcon = leadingIcon?.let {
                    { Icon(imageVector = it, contentDescription = null, tint = RestaurantColors.OnSurfaceVariant) }
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowDown,
                        contentDescription = null,
                        modifier = Modifier.rotate(arrowRotation),
                        tint = RestaurantColors.OnSurfaceVariant,
                    )
                },
                isError = isError,
                singleLine = true,
                shape = shape,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = if (isError) RestaurantColors.Error else focusedBorderColor,
                    unfocusedBorderColor = if (isError) RestaurantColors.Error else unfocusedBorderColor,
                    focusedLabelColor = if (isError) RestaurantColors.Error else focusedBorderColor,
                    unfocusedLabelColor = RestaurantColors.OnSurfaceVariant,
                    disabledContainerColor = RestaurantColors.SurfaceVariant,
                ),
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                containerColor = RestaurantColors.Surface,
                shape = RoundedCornerShape(12.dp),
            ) {
                items.forEach { item ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = itemLabel(item),
                                fontFamily = fontFamily,
                                color = if (selectedItem == item) RestaurantColors.Primary
                                else RestaurantColors.OnSurface,
                            )
                        },
                        onClick = {
                            onItemSelected(item)
                            expanded = false
                        },
                        colors = MenuDefaults.itemColors(
                            textColor = RestaurantColors.OnSurface,
                        ),
                    )
                }
            }
        }

        if (isError) {
            RestCaption(
                text = errorMessage ?: "",
                modifier = Modifier.padding(start = 12.dp, top = 4.dp),
                color = RestaurantColors.Error,
            )
        }
    }
}

// Sobrecarga simple para listas de String
@Composable
fun RestDropdown(
    selectedItem: String?,
    items: List<String>,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "",
    placeholder: String = "Seleccionar...",
    leadingIcon: ImageVector? = null,
    enabled: Boolean = true,
    errorMessage: String? = null,
    shape: Shape = RoundedCornerShape(12.dp),
    fontFamily: FontFamily = FontFamily.Default,
) = RestDropdown(
    selectedItem = selectedItem,
    items = items,
    onItemSelected = onItemSelected,
    itemLabel = { it },
    modifier = modifier,
    label = label,
    placeholder = placeholder,
    leadingIcon = leadingIcon,
    enabled = enabled,
    errorMessage = errorMessage,
    shape = shape,
    fontFamily = fontFamily,
)