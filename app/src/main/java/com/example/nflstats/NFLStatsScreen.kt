package com.example.nflstats

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.example.nflstats.data.Teams
import com.example.nflstats.model.Team
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.nflstats.model.Entity
import com.example.nflstats.ui.MainMenu
import com.example.nflstats.ui.SelectionMenu
import com.example.nflstats.ui.theme.defaultCardModifier
import com.example.nflstats.ui.theme.defaultImageModifier

enum class Menus(@StringRes val title : Int) {
    MainMenu(R.string.main_menu),
    TeamSelectionMenu(R.string.team_selection_menu),
    PlayerSelectionMenu(R.string.player_selection_menu)
}
@Composable
fun NFLStatsScreen(navController: NavHostController = rememberNavController()) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = Menus.valueOf(backStackEntry?.destination?.route ?: Menus.MainMenu.name)

    NavHost(navController = navController, startDestination = Menus.MainMenu.name) {
        composable(route = Menus.MainMenu.name) {
            MainMenu(
                { navController.navigate(Menus.TeamSelectionMenu.name) },
                { navController.navigate(Menus.PlayerSelectionMenu.name )},
                { }
            )
        }

        //Route for going to the menu to select a team's stats
        composable(route = Menus.TeamSelectionMenu.name) {
            //IMPORTANT *******************
            //NOTE: CHANGE OPTIONS LATER SO IT USES SYSTEM STORED OBJECTS, NOT NEWLY CREATED ONES
            //IMPORTANT *******************
            val options = Teams.entries.map { Team(it) }
            SelectionMenu(
                entities = options,
                onCardClick = {  },
                imageModifier = defaultImageModifier,
                cardModifier = defaultCardModifier
            )
        }

        composable(route = Menus.PlayerSelectionMenu.name) {
            SelectionMenu(
                entities = listOf<Entity>(),
                onCardClick = { },
                imageModifier = defaultImageModifier,
                cardModifier = defaultCardModifier
            )
        }
    }
}