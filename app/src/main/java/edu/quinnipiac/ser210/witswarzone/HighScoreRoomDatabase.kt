package edu.quinnipiac.ser210.witswarzone

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [HighScore::class], version = 1, exportSchema = false)
abstract class HighScoreRoomDatabase: RoomDatabase()
{
    abstract fun highScoreDao(): HighScoreDao

    companion object
    {
        @Volatile
        private var INSTANCE: HighScoreRoomDatabase? = null

        fun getDatabase(context: Context): HighScoreRoomDatabase
        {
            return INSTANCE ?: synchronized(this)
            {
                val instance = Room.databaseBuilder(
                    context.applicationContext.applicationContext,
                    HighScoreRoomDatabase::class.java,
                    "highScore_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                return instance
            }
        }
    }
}