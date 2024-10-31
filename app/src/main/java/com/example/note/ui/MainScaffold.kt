package com.example.note.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.note.data.local.model.UserData
import com.example.note.navigation.Screens
import com.example.note.navigation.navigateToSingleTop
import com.example.note.presentation.home.HomeViewModel

data class ScaffoldConfig(
  val showTopBar : Boolean = true,
  val showBottomBar : Boolean = true,
  val topBarTitle : String = "Note"
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
  scaffoldConfig : ScaffoldConfig,
  showBackButton : Boolean = false,
  onBackClick : () -> Unit = {}
) {
  if (scaffoldConfig.showTopBar) {
    TopAppBar(
      title = { Text(text = scaffoldConfig.topBarTitle) },
      navigationIcon = if (showBackButton) {
        {
          IconButton(onClick = onBackClick) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = "Back"
            )
          }
        }
      } else {
        {}
      }
    )
  }
}

@Composable
fun BottomBar(
  scaffoldConfig : ScaffoldConfig,
  navController : NavHostController,
  userData : UserData?,
  onLogout : () -> Unit,
  homeViewModel : HomeViewModel
) {
  if (scaffoldConfig.showBottomBar) {
    BottomAppBar {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
      ) {
        IconButtonBottomBar(
          navController = navController,
          route = Screens.Home.route,
          icon = Icons.Default.Home,
          contentDescription = "Home"
        )

        if (userData == null) {
          IconButtonBottomBar(
            navController = navController,
            route = Screens.Login.route,
            icon = Icons.AutoMirrored.Filled.Login,
            contentDescription = "Login"
          )
          IconButtonBottomBar(
            navController = navController,
            route = Screens.Register.route,
            icon = Icons.AutoMirrored.Filled.Help,
            contentDescription = "Register"
          )
        } else {
          // User actions
          UserActions(
            onLogout = onLogout,
            onCheckProfile = { homeViewModel.fetchProfile() }
          )
        }
      }
    }
  }
}

@Composable
fun MainScaffold(
  navController : NavHostController = rememberNavController(),
  userData : UserData? = null,
  onLogout : () -> Unit = {},
  homeViewModel : HomeViewModel = viewModel(),
  content : @Composable (PaddingValues) -> Unit = {},
) {
  val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

  val rootLayout : List<String> = listOf(
    Screens.Home.route,
    Screens.Login.route,
    Screens.Register.route
  )

  val scaffoldConfig = when (currentRoute) {
    Screens.Home.route     -> {
      ScaffoldConfig(
        showTopBar = true,
        showBottomBar = true,
        topBarTitle = "Home"
      )
    }

    Screens.Login.route    -> {
      ScaffoldConfig(
        showTopBar = true,
        showBottomBar = true,
        topBarTitle = "Login"
      )
    }

    Screens.Register.route -> {
      ScaffoldConfig(
        showTopBar = true,
        showBottomBar = true,
        topBarTitle = "Register"
      )
    }

    else                   -> {
      ScaffoldConfig(
        showTopBar = true,
        showBottomBar = false
      )
    }
  }

  Scaffold(
    topBar = {

      TopBar(
        scaffoldConfig,
        showBackButton = ! rootLayout.contains(currentRoute),
        onBackClick = { navController.popBackStack() }
      )

    },
    bottomBar = {
      BottomBar(
        scaffoldConfig = scaffoldConfig,
        navController = navController,
        userData = userData,
        onLogout = onLogout,
        homeViewModel = homeViewModel
      )
    }
  ) { contentPadding ->
    content(contentPadding)
  }
}

@Composable
private fun UserActions(
  onLogout : () -> Unit,
  onCheckProfile : () -> Unit
) {
  IconButton(
    onClick = onLogout,
    modifier = Modifier
      .size(48.dp)
      .clip(CircleShape)
      .background(Color.Red)
  ) {
    Icon(
      imageVector = Icons.AutoMirrored.Filled.Logout,
      contentDescription = "Logout"
    )
  }

  IconButton(
    onClick = onCheckProfile,
    modifier = Modifier
      .size(48.dp)
      .clip(CircleShape)
      .background(Color.Red)
  ) {
    Icon(
      imageVector = Icons.Default.VerifiedUser,
      contentDescription = "Check User Data"
    )
  }
}


@Composable
fun IconButtonBottomBar(
  navController : NavHostController,
  route : String,
  icon : ImageVector,
  contentDescription : String
) {
  val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
  val isSelected = currentRoute == route

  IconButton(
    onClick = {
      navController.navigateToSingleTop(route)
    },
    modifier = Modifier
      .size(48.dp)
      .clip(CircleShape)
      .background(
        if (isSelected) MaterialTheme.colorScheme.primary else Color.Red
      )
  ) {
    Icon(
      imageVector = icon,
      contentDescription = contentDescription
    )
  }
}