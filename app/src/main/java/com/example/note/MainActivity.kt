package com.example.note

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.note.di.UserPreferences
import com.example.note.navigation.NoteNavigation
import com.example.note.navigation.Screens
import com.example.note.presentation.detail.NoteDetailAssistedFactory
import com.example.note.presentation.home.HomeViewModel
import com.example.note.presentation.login.LoginViewModel
import com.example.note.presentation.register.RegisterViewModel
import com.example.note.ui.MainScaffold
import com.example.note.ui.theme.NoteTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  @Inject
  lateinit var userPreferences : UserPreferences

  @Inject
  lateinit var assistedFactory : NoteDetailAssistedFactory


  override fun onCreate(savedInstanceState : Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      val homeViewModel : HomeViewModel = viewModel()
      val registerViewModel : RegisterViewModel = viewModel()
      val loginViewModel : LoginViewModel = viewModel()
      val navController = rememberNavController()

      val userData by userPreferences.getUserData().collectAsState(initial = null)

      NoteTheme {
        MainScaffold(
          navController = navController,
          userData = userData,
          onLogout = {
            homeViewModel.viewModelScope.launch {
              userPreferences.clearUserSession()
              navController.navigate(Screens.Home.route) {
                popUpTo(0)
              }

            }
          },
          homeViewModel = homeViewModel
        ) { paddingValues ->
          Column(
            modifier = Modifier.padding(paddingValues)
          ) {
            NoteNavigation(
              navHostController = navController,
              homeViewModel = homeViewModel,
              registerViewModel = registerViewModel,
              loginViewModel = loginViewModel,
              assistedFactory = assistedFactory
            )
          }
        }
      }
    }
  }
}
