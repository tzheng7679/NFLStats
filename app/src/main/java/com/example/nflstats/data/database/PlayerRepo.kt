package com.example.nflstats.data.database
import com.example.nflstats.model.Player
import kotlinx.coroutines.flow.Flow

class PlayerRepo(private val playerDao: PlayerDao) {
    suspend fun replaceOrInsert(player: Player) { return playerDao.replaceOrInsert(player) }

    fun getPlayer(id: Int): Flow<Player?> { return playerDao.getPlayer(id) }

    fun getAllPlayers(): Flow<List<Player>?> { return playerDao.getAllPlayers() }
    fun getFirst(): Flow<Player?> { return playerDao.getFirst() }
}