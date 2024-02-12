package com.example.nflstats

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.nflstats.data.StatViewModel
import com.example.nflstats.data.Teams
import com.example.nflstats.data.teamImageMap
import com.example.nflstats.model.Entity
import com.example.nflstats.model.Player
import com.example.nflstats.model.Team
import com.example.nflstats.ui.MainMenu
import com.example.nflstats.ui.SelectionMenu
import com.example.nflstats.ui.StatViewMenu
import com.example.nflstats.ui.theme.defaultPlayerImageModifier
import com.example.nflstats.ui.theme.defaultTeamImageModifier
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

enum class Menus(@StringRes val title : Int) {
    MainMenu(R.string.main_menu),
    TeamSelectionMenu(R.string.team_selection_menu),
    PlayerSelectionMenu(R.string.player_selection_menu),
    StatViewMenu(R.string.stat_view_menu)
}
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun NFLStatsScreen(
    viewModel: StatViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val uiState by viewModel.uiState.collectAsState()
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
            val context = LocalContext.current
            val options = Teams.entries.map { Team(it) }
            SelectionMenu(
                entities = options,
                onCardClick = { entity ->
                    viewModel.setEntity(entity)
                    viewModel.setStats(entity.fetchStatValues(context = context))
                    navController.navigate(Menus.StatViewMenu.name)
                              },
                imageModifier = defaultTeamImageModifier
            )
        }

        //Route for going to the menu to select a player's stats
        composable(route = Menus.PlayerSelectionMenu.name) {
            SelectionMenu(
                entities = listOf<Entity>(),
                onCardClick = { },
                imageModifier = defaultPlayerImageModifier
            )
        }

        //Route for going to see the
        composable(route = Menus.StatViewMenu.name) {
            val currEntity = uiState.currEntity ?: Player("Error", "Player could not be fetched", imageID = teamImageMap[Teams.WSH]!!, playerID = 0)
            viewModel.setStats(currEntity.fetchStatValues(context = LocalContext.current))
            val stats = uiState.currStats

            StatViewMenu(uiState = uiState, "secondary filler")
        }
    }
}