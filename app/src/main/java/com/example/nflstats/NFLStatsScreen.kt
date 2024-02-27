package com.example.nflstats

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nflstats.ui.StatViewModel
import com.example.nflstats.data.Status
import com.example.nflstats.data.Teams
import com.example.nflstats.data.database.AppDataContainer
import com.example.nflstats.model.Player
import com.example.nflstats.model.Team
import com.example.nflstats.ui.StatSettingsViewModel
import com.example.nflstats.ui.screens.MainMenu
import com.example.nflstats.ui.screens.SelectionMenu
import com.example.nflstats.ui.screens.StatSettingsMenu
import com.example.nflstats.ui.screens.StatViewMenu
import com.example.nflstats.ui.theme.defaultPlayerImageModifier
import com.example.nflstats.ui.theme.defaultTeamImageModifier


enum class Menus(@StringRes val title : Int) {
    MainMenu(R.string.main_menu),
    TeamSelectionMenu(R.string.team_selection_menu),
    FromMainPlayerSelectionMenu(R.string.from_main_player_selection_menu),
    FromTeamPlayerSelectionMenu(R.string.from_team_player_selection_menu),
    StatViewMenuForTeam(R.string.stat_view_menu),
    StatViewMenuForPlayer(R.string.stat_view_menu_true),
    StatSettingsEntry(R.string.stat_settings_menu_entry),
    StatSettingsMenu(R.string.stat_settings_menu)
}

@RequiresApi(Build.VERSION_CODES.R)
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun NFLStatsScreen(
    viewModel: StatViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val appDataContainer: AppDataContainer = AppDataContainer(LocalContext.current)
    val statSettingsViewModel: StatSettingsViewModel = StatSettingsViewModel(appDataContainer.teamsRepo, appDataContainer.playersRepo)

    val uiState by viewModel.uiState.collectAsState()
    NavHost(navController = navController, startDestination = Menus.MainMenu.name) {
        //The main menu
        composable(route = Menus.MainMenu.name) {
            MainMenu(
                { navController.navigate(Menus.TeamSelectionMenu.name) },
                { navController.navigate(Menus.FromMainPlayerSelectionMenu.name) },
                { navController.navigate(Menus.StatSettingsMenu.name) }
            )
        }

        //Route for going to the menu to select a team, and then to StatViewMenu upon selection of team
        composable(route = Menus.TeamSelectionMenu.name) {
            //IMPORTANT *******************
            //NOTE: CHANGE OPTIONS LATER SO IT USES SYSTEM STORED OBJECTS, NOT NEWLY CREATED ONES
            //IMPORTANT *******************
            val options = Teams.entries.map { Team(it) }
            SelectionMenu<Team>(
                //can assume that teams will be succesfuly created
                status = Status.SUCCESS,
                entities = options,
                onCardClick = { team ->
                    viewModel.setTeam(team)
                    navController.navigate(Menus.StatViewMenuForTeam.name)
                              },
                imageModifier = defaultTeamImageModifier
            )
        }

        //Route for going to the menu to select a player's stats from a team
        composable(route = Menus.FromTeamPlayerSelectionMenu.name) {
            SelectionMenu<Player>(
                status = uiState.currPlayerListStatus,
                entities = uiState.currPlayerList ?: emptyList(),
                onCardClick = {
                    viewModel.setPlayer(player = it)
                    navController.navigate(Menus.StatViewMenuForPlayer.name)
                },
                imageModifier = defaultPlayerImageModifier
            )
        }

        //Route for going to the menu to select a player's stats from the main menu
        composable(route = Menus.FromMainPlayerSelectionMenu.name) {
            val options = Teams.entries.map { Team(it) }
            SelectionMenu<Team>(
                //can assume that teams will be succesfuly created
                status = Status.SUCCESS,
                entities = options,
                onCardClick = { team ->
                    viewModel.setTeam(team)
                    viewModel.setPlayers()
                    navController.navigate(Menus.FromTeamPlayerSelectionMenu.name)
                },
                imageModifier = defaultTeamImageModifier
            )
        }

        //Route for going to see the statistics for a team (assumes that the stats have already been set)
        composable(route = Menus.StatViewMenuForTeam.name) {
            StatViewMenu(
                uiState = uiState,
                onGetPlayers = {
                    viewModel.setPlayers()
                    navController.navigate(Menus.FromTeamPlayerSelectionMenu.name)
                },
                viewPlayer = false
            )
        }

        composable(route = Menus.StatViewMenuForPlayer.name) {
            StatViewMenu(
                uiState = uiState,
                onGetPlayers = {},
                viewPlayer = true,
            )
        }

        composable(route = Menus.StatSettingsEntry.name) {
            StatSettingsMenu(
                toGlobalTeams = {},
                toTeams = {},
                toGlobalPlayers = {},
                toPlayers = {},
            )
        }

        composable(route = Menus.StatSettingsMenu.name) {

        }
    }
}