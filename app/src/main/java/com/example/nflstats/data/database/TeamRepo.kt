package com.example.nflstats.data.database
import com.example.nflstats.model.Team
import kotlinx.coroutines.flow.Flow
class TeamRepo(private val teamDao: TeamDao) {
    suspend fun replaceOrInsert(team: Team) {
        return teamDao.replaceOrInsert(team)
    }

    fun getTeam(id: Int): Flow<Team?> {
        return teamDao.getTeam(id)
    }

    fun getAllTeams(): Flow<List<Team>?> {
        return teamDao.getAllTeams()
    }

    fun getFirst(): Flow<Team?> {
        return teamDao.getFirst()
    }

    suspend fun clearTeams() {
        return teamDao.clearTeams()
    }
}