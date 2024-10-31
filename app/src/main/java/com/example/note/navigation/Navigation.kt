package com.example.note.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.note.presentation.detail.NoteDetailAssistedFactory
import com.example.note.presentation.detail.NoteDetailScreen
import com.example.note.presentation.home.HomeScreen
import com.example.note.presentation.home.HomeViewModel
import com.example.note.presentation.login.LoginScreen
import com.example.note.presentation.login.LoginViewModel
import com.example.note.presentation.register.RegisterScreen
import com.example.note.presentation.register.RegisterViewModel

sealed interface Screens {

  val route : String

  data object Home : Screens {

    override val route = "home"
  }

  data object Login : Screens {

    override val route = "login"
  }

  data object Register : Screens {

    override val route = "register"
  }

  data object NoteDetail : Screens {

    override val route = "note/{noteId}"
    const val NOTE_ID = "noteId"

    fun createRoute(noteId : String) = "note/$noteId"
  }
}

fun NavHostController.navigateToSingleTop(route : String) {
  this.navigate(route) {
    this.popUpTo(graph.findStartDestination().id) {
      saveState = true
    }
    launchSingleTop = true
    restoreState = true
  }
}

@Composable
fun NoteNavigation(
  navHostController : NavHostController,
  homeViewModel : HomeViewModel,
  registerViewModel : RegisterViewModel,
  loginViewModel : LoginViewModel,
  assistedFactory : NoteDetailAssistedFactory,
) {
  NavHost(
    navController = navHostController,
    startDestination = Screens.Home.route
  ) {
    fun navigateTo(route : String) {
      navHostController.navigateToSingleTop(route)
    }
    composable(Screens.Home.route) {

      HomeScreen(
        vm = homeViewModel,
        onNoteClick = { noteId ->
          navHostController.navigate(Screens.NoteDetail.createRoute(noteId))
        }
      )
    }
    composable(Screens.Login.route) {

      LoginScreen(
        vm = loginViewModel,
        navigateToHome = {
          navigateTo(Screens.Home.route)
        }

      )
    }
    composable(Screens.Register.route) {

      RegisterScreen(
        vm = registerViewModel,
        navigateToLogin = {
          navigateTo(Screens.Login.route)
        }
      )
    }
    composable(
      route = Screens.NoteDetail.route,
      arguments = listOf(
        navArgument(Screens.NoteDetail.NOTE_ID) {
          type = NavType.StringType
        }
      )
    ) { backStackEntry ->

      val noteId = backStackEntry.arguments?.getString(Screens.NoteDetail.NOTE_ID) ?: ""

      NoteDetailScreen(
        assistedFactory = assistedFactory,
        id = noteId,
        onBackClick = {
          navHostController.popBackStack()
        }
      )
    }

  }
}