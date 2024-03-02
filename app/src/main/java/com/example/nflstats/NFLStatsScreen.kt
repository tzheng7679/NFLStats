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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.nflstats.data.Status
import com.example.nflstats.data.Teams
import com.example.nflstats.data.database.AppDataContainer
import com.example.nflstats.model.Entity
import com.example.nflstats.model.Player
import com.example.nflstats.model.Team
import com.example.nflstats.ui.StatSettingsViewModel
import com.example.nflstats.ui.StatViewModel
import com.example.nflstats.ui.screens.MainMenu
import com.example.nflstats.ui.screens.SelectionMenu
import com.example.nflstats.ui.screens.StatSettingsMenu
import com.example.nflstats.ui.screens.StatViewMenu
import com.example.nflstats.ui.screens.StatsChangeMenu
import com.example.nflstats.ui.theme.defaultPlayerImageModifier
import com.example.nflstats.ui.theme.defaultTeamImageModifier
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking


enum class Menus(@StringRes val title : Int) {
    MainMenu(R.string.main_menu),
    TeamSelectionMenu(R.string.team_selection_menu),
    FromMainPlayerSelectionMenu(R.string.from_main_player_selection_menu),
    FromTeamPlayerSelectionMenu(R.string.from_team_player_selection_menu),
    SavedPlayersSelectionMenu(R.string.saved_players_selection_menu),
    StatViewMenu(R.string.stat_view_menu),
    StatSettingsEntry(R.string.stat_settings_menu_entry),
    StatSettingsSelectionMenu(R.string.stat_settings_menu),
    StatSettingsChangeMenu(R.string.stat_settings_change_menu),
    GlobalStatSettingsChangeMenu(R.string.global_stat_settings_change_menu)
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
                onTeamSelectionButton = { navController.navigate(Menus.TeamSelectionMenu.name) },
                onPlayerSelectionButton = { navController.navigate(Menus.FromMainPlayerSelectionMenu.name) },
                onSavedPlayerSelectionButton = { navController.navigate(Menus.SavedPlayersSelectionMenu.name) },
                onChangeStatsButton = { navController.navigate(Menus.StatSettingsEntry.name) }
            )
        }

        //Route for going to the menu to select a team, and then to StatViewMenu upon selection of team
        composable(route = Menus.TeamSelectionMenu.name) {
            //! IMPORTANT *******************
            //! NOTE: CHANGE OPTIONS LATER SO IT USES SYSTEM STORED OBJECTS, NOT NEWLY CREATED ONES
            //! IMPORTANT *******************
            //! val options = Teams.entries.map { Team(it) }
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
                    val x = uiState.currTeamStats
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
                entities = runBlocking { statSettingsViewModel.getPlayersWithReplace(uiState.currPlayerList) }
                    .sortedBy { it.formattedName.second },
                onCardClick = { player ->
                    viewModel.setPlayer(player = player)
                    navController.navigate(Menus.StatViewMenu.name + "/" + "true")
                },
                imageModifier = defaultPlayerImageModifier
            )
        }

        composable(route = Menus.SavedPlayersSelectionMenu.name) {
            SelectionMenu<Player>(
                status = Status.SUCCESS,
                entities = runBlocking { statSettingsViewModel.getAllPlayers().first() ?: emptyList() }
                    .sortedBy { it.formattedName.second },
                imageModifier = defaultPlayerImageModifier,
                onCardClick = { player ->
                    viewModel.setPlayer(player = player)
                    navController.navigate(Menus.StatViewMenu.name + "/" + "true")
                }
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
        composable(route = Menus.StatViewMenu.name + "/{viewPlayer}",
            arguments = listOf(navArgument("viewPlayer") { type = NavType.BoolType })
        ) {
            StatViewMenu(
                uiState = uiState,
                onGetPlayers = {
                    viewModel.setPlayers()
                    navController.navigate(Menus.FromTeamPlayerSelectionMenu.name)
                },
                onAddEntity = {
                    runBlocking {
                        if(it is Team) {
                            statSettingsViewModel.upsert(it)
                            navController.navigate(Menus.StatSettingsChangeMenu.name + "/" + it.id + "/" + "false")
                        }
                        else if(it is Player) {
                            statSettingsViewModel.upsertPlayer(it)
                            navController.navigate(Menus.StatSettingsChangeMenu.name + "/" + it.id + "/" + "true")
                        }
                    }
                },
                viewPlayer = it.arguments?.getBoolean("viewPlayer") ?: false
            )
        }

        //Entry route for settings for entities
        composable(route = Menus.StatSettingsEntry.name) {
            StatSettingsMenu(
                toTeams = { navController.navigate(Menus.StatSettingsSelectionMenu.name + "/" + "false") },
                toPlayers = { navController.navigate(Menus.StatSettingsSelectionMenu.name + "/" + "true") },
                toGlobalPlayers = { navController.navigate(Menus.GlobalStatSettingsChangeMenu.name + "/" + "true")},
                toGlobalTeams = { navController.navigate(Menus.GlobalStatSettingsChangeMenu.name + "/" + "false") }
            )
        }

        //Selection menu for choosing what entity to change settings for
        composable(route = Menus.StatSettingsSelectionMenu.name + "/{forPlayer}",
            arguments = listOf(navArgument("forPlayer") { type = NavType.BoolType })
        ) {
            SelectionMenu<Entity>(
                entities = when (it.arguments?.getBoolean("forPlayer") ?: false) {
                        true -> statSettingsUIState.value.playerSettingsList ?: emptyList()
                        false -> statSettingsUIState.value.teamSettingsList ?: emptyList()
                    }.sortedBy { it.formattedName.first },
                onCardClick = { entity ->
                    navController.navigate(Menus.StatSettingsChangeMenu.name + "/" + entity.id + "/" + "${entity is Player}")
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

        //Menu for choosing what stats to deactivate (only for one entity)
        composable(route = Menus.StatSettingsChangeMenu.name + "/{id}/{isPlayer}",
            arguments = listOf(navArgument("id") { type = NavType.IntType}, navArgument("isPlayer") {type = NavType.BoolType})
        ) {
            val isPlayer = it.arguments?.getBoolean("isPlayer") ?: false
            val id = it.arguments?.getInt("id")
            if(id != null) {
                StatsChangeMenu(
                    options = runBlocking { statSettingsViewModel.getStatsShowMap(id = id, forPlayer = isPlayer) },
                    onUpdate = {
                        runBlocking {
                            statSettingsViewModel.setUpdatedEntity(newStats = it, id = id, forPlayer = isPlayer)
                            navController.navigateUp()
                            navController.navigateUp()
                        }
                    }
                )
            }
        }

        //Menu for choosing what stats to deactivate (but for all teams if isPlayer and players otherwise)
        composable(
            route = Menus.GlobalStatSettingsChangeMenu.name + "/{isPlayer}",
            arguments = listOf(navArgument("isPlayer") {type = NavType.BoolType})
        ) {
            val isPlayer = it.arguments?.getBoolean("isPlayer") ?: false
            StatsChangeMenu(
                options = runBlocking { statSettingsViewModel.getStatSuperSet(isPlayer = isPlayer).associateWith { false } },
                onUpdate = {newStats ->
                    runBlocking {
                        statSettingsViewModel.setAllStats(newStats = newStats, forPlayer = isPlayer)
                        navController.navigateUp()
                        navController.navigateUp()
                    }
                }
            )
        }
    }
}