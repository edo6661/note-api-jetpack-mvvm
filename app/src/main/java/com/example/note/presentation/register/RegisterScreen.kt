package com.example.note.presentation.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.note.ui.shared.CircularCenteredProgressIndicator
import com.example.note.ui.shared.ErrorText
import com.example.note.ui.shared.PrimaryTextField

@Composable
fun RegisterScreen(
  vm : RegisterViewModel,
  navigateToLogin : () -> Unit
) {
  val uiState by vm.uiState.collectAsState()

  if (uiState.isLoading) {
    CircularCenteredProgressIndicator()
  }
  Column(modifier = Modifier.padding(16.dp)) {
    Text(text = "Register Screen", modifier = Modifier.padding(bottom = 16.dp))

    PrimaryTextField(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp),
      label = "Username",
      placeholder = "Enter your username",
      value = uiState.username,
      onChange = {
        vm.onEvent(RegisterEvent.UsernameChanged(it))
      }
    )
    PrimaryTextField(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp),
      label = "Email",
      placeholder = "Enter your email",
      value = uiState.email,
      onChange = {
        vm.onEvent(RegisterEvent.EmailChanged(it))
      }
    )

    PrimaryTextField(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp),
      label = "Password",
      placeholder = "Enter your password",
      isPassword = true,
      value = uiState.password,
      onChange = {
        vm.onEvent(RegisterEvent.PasswordChanged(it))
      }
    )

    Button(
      onClick = {
        vm.onEvent(RegisterEvent.Submit)
      },
      modifier = Modifier
        .fillMaxWidth()
        .padding(top = 16.dp)
    ) {
      Text("Register")
    }
    uiState.error?.let { errMsg ->
      ErrorText(errMsg)
    }

  }
}
