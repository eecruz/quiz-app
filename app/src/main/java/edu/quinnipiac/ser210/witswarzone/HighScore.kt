package edu.quinnipiac.ser210.witswarzone

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "highscores")
data class HighScore(
    @PrimaryKey(autoGenerate = true)
    val _id: Int = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "score")
    val score: Int
): Parcelable
{
    override fun toString(): String
    {
        super.toString()
        return "Name: $name,  Score: $score"
    }
}