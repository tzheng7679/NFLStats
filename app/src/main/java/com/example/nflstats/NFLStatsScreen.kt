package com.example.nflstats

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.height
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
import com.example.nflstats.ui.screens.StatsChangeMenu
import com.example.nflstats.ui.theme.defaultPlayerImageModifier
import com.example.nflstats.ui.theme.defaultTeamImageModifier
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlin.reflect.KClass
import kotlin.reflect.KTypeParameter


enum class Menus(@StringRes val title : Int) {
    MainMenu(R.string.main_menu),
    TeamSelectionMenu(R.string.team_selection_menu),
    FromMainPlayerSelectionMenu(R.string.from_main_player_selection_menu),
    FromTeamPlayerSelectionMenu(R.string.from_team_player_selection_menu),
    StatViewMenu(R.string.stat_view_menu),
    StatSettingsEntry(R.string.stat_settings_menu_entry),
    StatSettingsSelectionMenu(R.string.stat_settings_menu),
    StatSettingsChangeMenu(R.string.stat_settings_change_menu)
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
    statSettingsViewModel.build()

    val uiState by viewModel.uiState.collectAsState()
    val statSettingsUIState = statSettingsViewModel.uiState.collectAsState()

    NavHost(navController = navController, startDestination = Menus.MainMenu.name) {
        //The main menu
        composable(route = Menus.MainMenu.name) {
            MainMenu(
                { navController.navigate(Menus.TeamSelectionMenu.name) },
                { navController.navigate(Menus.FromMainPlayerSelectionMenu.name) },
                { navController.navigate(Menus.StatSettingsEntry.name) }
            )
        }

        //Route for going to the menu to select a team, and then to StatViewMenu upon selection of team
        composable(route = Menus.TeamSelectionMenu.name) {
            //! IMPORTANT *******************
            //! NOTE: CHANGE OPTIONS LATER SO IT USES SYSTEM STORED OBJECTS, NOT NEWLY CREATED ONES
            //! IMPORTANT *******************
            val options = Teams.entries.map { Team(it) }
            SelectionMenu<Team>(
                //can assume that teams will be succesfuly created
                status = Status.SUCCESS,
                entities = runBlocking {
                    val storedTeams = (statSettingsViewModel.getAllTeams().first() ?: emptyList()).toMutableList()
                    val storedAbbrs = storedTeams.map { it -> it.abbr }
                    Teams.entries.forEach {
                        if(it !in storedAbbrs) storedTeams.add(Team(it))
                    }
                    storedTeams
               }.sortedBy { it.formattedName.first },
                onCardClick = { team ->
                    viewModel.setTeam(team)
                    navController.navigate(Menus.StatViewMenu.name + "/" + "false")
                              },
                imageModifier = defaultTeamImageModifier
            )
        }

        //Route for going to the menu to select a player's stats from a team
        composable(route = Menus.FromTeamPlayerSelectionMenu.name) {
            SelectionMenu<Player>(
                status = uiState.currPlayerListStatus,
                entities = (uiState.currPlayerList?: emptyList()).sortedBy { it.formattedName.second } ?: emptyList(),
                onCardClick = {
                    viewModel.setPlayer(player = it)
                    navController.navigate(Menus.StatViewMenu.name + "/" + "true")
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
                entities = options.sortedBy { it.formattedName.first },
                onCardClick = { team ->
                    viewModel.setTeam(team)
                    viewModel.setPlayers()
                    navController.navigate(Menus.FromTeamPlayerSelectionMenu.name)
                },
                imageModifier = defaultTeamImageModifier
            )
        }

        //Route for going to see the statistics for a team (assumes that the stats have already been set)
        composable(
            route = Menus.StatViewMenu.name + "/{viewPlayer}",
            arguments = listOf(navArgument("viewPlayer") { type = NavType.BoolType })
        ) {
            StatViewMenu(
                uiState = uiState,
                onGetPlayers = {
                    viewModel.setPlayers()
                    navController.navigate(Menus.FromTeamPlayerSelectionMenu.name)
                },
                viewPlayer = it.arguments?.getBoolean("viewPlayer") ?: false,
                onAddEntity = {
                    runBlocking {
                        if(it is Team) {
                            Log.d("HelpMe", it.toString())
                            Log.d("HelpMe", "----------_-----")
                            Log.d("HelpMe", statSettingsUIState.value.teamSettingsList.toString())
                            statSettingsViewModel.upsert(it)
                            Log.d("HelpMe", statSettingsUIState.value.teamSettingsList.toString())
                        }
                        else if(it is Player) {
                            Log.d("HelpMe", it.toString())
                            Log.d("HelpMe", "----------_-----")
                            Log.d("HelpMe", statSettingsUIState.value.playerSettingsList.toString())
                            statSettingsViewModel.upsertPlayer(it)
                            Log.d("HelpMe", statSettingsUIState.value.playerSettingsList.toString())
                        }
                    }
                }
            )
        }

        composable(route = Menus.StatSettingsEntry.name) {
            StatSettingsMenu(
                toTeams = { navController.navigate(Menus.StatSettingsSelectionMenu.name + "/" + "false") },
                toPlayers = { navController.navigate(Menus.StatSettingsSelectionMenu.name + "/" + "true") }
            )
        }

        composable(
            route = Menus.StatSettingsSelectionMenu.name + "/{forPlayer}",
            arguments = listOf(navArgument("forPlayer") { type = NavType.BoolType })
        ) {
            SelectionMenu(
                entities = when (it.arguments?.getBoolean("forPlayer") ?: false) {
                        true -> statSettingsUIState.value.playerSettingsList ?: emptyList()
                        false -> statSettingsUIState.value.teamSettingsList ?: emptyList()
                    }.sortedBy { it.formattedName.first },
                onCardClick = { player ->
                    navController.navigate(Menus.StatSettingsChangeMenu.name + "/" + player.id + "/")
                },
                imageModifier = defaultPlayerImageModifier,
                status = Status.SUCCESS,
                onClear = {
                    when (it.arguments?.getBoolean("forPlayer") ?: false) {
                        true -> runBlocking {statSettingsViewModel.clearPlayers()}
                        false -> runBlocking {statSettingsViewModel.clearTeams()}
                    }
                }
            )
        }

//        composable(
//            route = Menus.StatSettingsChangeMenu.name + "/{id}/{isPlayer}",
//            arguments = listOf(navArgument("id") { type = NavType.IntType}, navArgument("isPlayer") {type = NavType.BoolType})
//        ) {
//            StatsChangeMenu(
//                options = {
//                    viewModel.set
//                }
//            )
//        }
    }
}