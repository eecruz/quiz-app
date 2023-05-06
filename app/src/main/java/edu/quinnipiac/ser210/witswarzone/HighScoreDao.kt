/*
    App:   WITSWARZONE
    Names: Emilio Cruz, William Siri
    Date: May 2023
 */

package edu.quinnipiac.ser210.witswarzone

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface HighScoreDao
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(highscore: HighScore)

    @Update
    suspend fun update(highscore: HighScore)

    @Delete
    suspend fun delete(highscore: HighScore)

    @Query("DELETE FROM highscores")
    suspend fun deleteAllScores()

    @Query("SELECT * from highscores WHERE _id =:id")
    fun getScore(id: Int): Flow<HighScore>

    @Query("SELECT * from highscores ORDER BY score DESC")
    fun getAllScores(): Flow<List<HighScore>>
}