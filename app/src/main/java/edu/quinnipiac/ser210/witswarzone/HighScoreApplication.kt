/*
    App:   WITSWARZONE
    Names: Emilio Cruz, William Siri
    Date: May 2023
 */

package edu.quinnipiac.ser210.witswarzone

import android.app.Application
import androidx.room.RoomDatabase

class HighScoreApplication: Application()
{
    val database: HighScoreRoomDatabase by lazy {HighScoreRoomDatabase.getDatabase(this)}
}