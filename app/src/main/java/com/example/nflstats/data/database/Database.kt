package com.example.nflstats.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.nflstats.model.Player
import com.example.nflstats.model.Team

@Database(entities = [Player::class, Team::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class EntityDatabase: RoomDatabase() {
    companion object {
        @Volatile
        private var Instance: EntityDatabase? = null

        fun getDatabase(context: Context): EntityDatabase {
            return Instance ?: synchronized(this) {
                Room
                    .databaseBuilder(context, EntityDatabase::class.java, "entities_database")
                    .fallbackToDestructiveMigration().build()
                    .also {
                        Instance = it
                    }
            }
        }
    }
    abstract fun getTeamDao(): TeamDao
    abstract fun getPlayerDao(): PlayerDao
}