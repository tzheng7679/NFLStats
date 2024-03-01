package com.example.nflstats.data.database
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
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

    @Query("SELECT * FROM players WHERE id = :id")
    fun getPlayer(id: Int): Flow<Player>

    @Query("SELECT * FROM players ORDER BY lName ASC")
    fun getAllPlayers(): Flow<List<Player>>

    @Query("SELECT * FROM players LIMIT 1")
    fun getFirst(): Flow<Player>
}