package com.droiddude.apps.mvvmmovies.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Movie
import androidx.compose.material.icons.rounded.Upcoming
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.droiddude.apps.mvvmmovies.presentation.movielist.MovieListUiEvent
import com.droiddude.apps.mvvmmovies.presentation.movielist.MovieListViewModel
import com.droiddude.apps.mvvmmovies.utils.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController : NavHostController) {
    val movieListViewModel = hiltViewModel<MovieListViewModel>()
    val movieListState = movieListViewModel.movieListState.collectAsState().value
    val bottomNavController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if(movieListState.isCurrentPopularScreen)
                        "Popular Movies"
                        else
                        "Upcoming Movies",
                        fontSize = 20.sp
                    )
                },
                modifier = Modifier.shadow(5.dp),
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    MaterialTheme.colorScheme.inverseOnSurface
                ))
        },
        bottomBar = {
            BottomNavigationBar(
                bottomNavController = bottomNavController,
                onEvent = movieListViewModel::onEvent
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            NavHost(
                navController = bottomNavController,
                startDestination = Screen.PopularMovies.route
            ) {
                composable(Screen.PopularMovies.route) {
                    PopularMoviesScreen(
                        movieListState = movieListState,
                        navController = navController,
                        onEvent = movieListViewModel::onEvent
                    )
                }
                composable(Screen.UpcomingMovies.route) {
                    UpcomingMoviesScreen(
                        movieListState = movieListState,
                        navController = navController,
                        onEvent = movieListViewModel::onEvent
                    )
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    bottomNavController : NavHostController,
    onEvent : (MovieListUiEvent) -> Unit
) {
    val items = listOf(
        BottomItem(
            title = "Popular",
            icon = Icons.Rounded.Movie
        ),
        BottomItem(
            title = "Upcoming",
            icon = Icons.Rounded.Upcoming
        )
    )

    val selected = rememberSaveable {
        mutableIntStateOf(0)
    }

    NavigationBar {
        Row(
            modifier = Modifier.background(MaterialTheme.colorScheme.inverseOnSurface)
        ) {
            items.forEachIndexed { index, bottomItem ->
                NavigationBarItem(
                    selected = selected.intValue == index,
                    onClick = {
                        selected.intValue = index
                        when(selected.intValue) {
                            0 -> {
                                onEvent(MovieListUiEvent.Navigate)
                                bottomNavController.popBackStack()
                                bottomNavController.navigate(Screen.PopularMovies.route)
                            }
                            1 -> {
                                onEvent(MovieListUiEvent.Navigate)
                                bottomNavController.popBackStack()
                                bottomNavController.navigate(Screen.UpcomingMovies.route)
                            }
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = bottomItem.icon,
                            contentDescription = bottomItem.title,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    },
                    label = {
                        Text(
                            text = bottomItem.title, color = MaterialTheme.colorScheme.onBackground
                        )
                    })
            }
        }
    }
}

data class BottomItem(
    val title : String,
    val icon: ImageVector
)