package com.example.note.ui.shared

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
@Preview(showSystemUi = true)
fun PrimaryTextField(
  modifier : Modifier = Modifier,
  isEnabled : Boolean = true,
  label : String = "Label",
  placeholder : String = "Placeholder",
  isError : Boolean = false,
  value : String = "",
  icon : @Composable (() -> Unit)? =
//    {
//    Icon(
//      Icons.Filled.Search,
//      contentDescription = "Search Icon"
//    )
//  }
    null,
  isPassword : Boolean = false,
  onChange : (String) -> Unit = {}
) {
  TextField(
    modifier = modifier
      .clip(RoundedCornerShape(8.dp)),
    value = value,
    onValueChange = {
      onChange(it)
    },
    label = { Text(label) },
    placeholder = { Text(placeholder) },
    enabled = isEnabled,
    singleLine = true,
    isError = isError,
    leadingIcon = icon,
    visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
    keyboardOptions = KeyboardOptions(
      keyboardType = if (isPassword) KeyboardType.Password else KeyboardType.Text
    ),
    trailingIcon = {
      if (value.isNotEmpty()) {
        IconButton(onClick = { onChange("") }) {
          Icon(Icons.Filled.Clear, contentDescription = "Clear Text")
        }
      }
    }
  )
}
