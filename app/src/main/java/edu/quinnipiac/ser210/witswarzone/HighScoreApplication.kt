package edu.quinnipiac.ser210.witswarzone

import android.app.Application
import androidx.room.RoomDatabase

class HighScoreApplication: Application()
{
    val database: RoomDatabase by lazy {HighScoreRoomDatabase.getDatabase(this)}
}