package com.example.nflstats.data.database
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.nflstats.model.Team
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamDao {
    /**
     * Replace team in the database, or insert them if not there already
     */
    @Upsert
    suspend fun replaceOrInsert(team: Team)

    /**
     * Returns team of id [id] in the database
     */
    @Query("SELECT * FROM teams WHERE id = :id")
    fun getTeam(id: Int): Flow<Team>

    /**
     * Returns all teams stored in the database
     */
    @Query("SELECT * FROM teams ORDER BY abbr ASC")
    fun getAllTeams(): Flow<List<Team>>

    /**
     * Returns the first team in the table
     */
    @Query("SELECT * FROM teams LIMIT 1")
    fun getFirst(): Flow<Team>

    /**
     * Clears all the team from the table
     */
    @Query("DELETE FROM teams")
    suspend fun clearTeams()
}