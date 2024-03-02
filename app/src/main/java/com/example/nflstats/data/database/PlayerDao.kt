package com.example.nflstats.data.database
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.nflstats.model.Player
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao {
    /**
     * Replace Player in the database, or insert them if not there already
     */
    @Upsert
    suspend fun replaceOrInsert(player: Player)

    /**
     * Returns Flow of player with id [id]
     */
    @Query("SELECT * FROM players WHERE id = :id")
    fun getPlayer(id: Int): Flow<Player>

    /**
     * Returns all players in database
     */
    @Query("SELECT * FROM players ORDER BY lName ASC")
    fun getAllPlayers(): Flow<List<Player>>

    /**
     * Returns the first player in the database
     */
    @Query("SELECT * FROM players LIMIT 1")
    fun getFirst(): Flow<Player>

    /**
     * Clears all the players stored in the database
     */
    @Query("DELETE FROM players")
    suspend fun clearPlayers()
}