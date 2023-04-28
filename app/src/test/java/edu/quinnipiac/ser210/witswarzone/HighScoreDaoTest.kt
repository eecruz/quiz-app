package edu.quinnipiac.ser210.witswarzone

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith

import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.*
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class HighScoreDaoTest {

    private lateinit var highScoreDao: HighScoreDao
    private lateinit var highScoreRoomDatabase: HighScoreRoomDatabase
    private var highScore1 = HighScore(1,"Guest1",7)
    private var highscore2 = HighScore(2,"Guest2",5)

    @Before
    fun createDB() {
        val context: Context = ApplicationProvider.getApplicationContext()

        highScoreRoomDatabase = Room.inMemoryDatabaseBuilder(context, HighScoreRoomDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        highScoreDao = highScoreRoomDatabase.highScoreDao()
    }

    private suspend fun addOneHighScoreToDb() {
        highScoreDao.insert(highScore1)
    }
    private suspend fun addTwoHighScoresToDb() {
        highScoreDao.insert(highScore1)
        highScoreDao.insert(highscore2)
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_insertsHighScoreIntoDB() = runBlocking {
        addOneHighScoreToDb()
        val allHighScores = highScoreDao.getAllScores().first()
        assertEquals(allHighScores, highScore1)
    }
    @Test
    @Throws(Exception::class)
    fun daoGetAllHighScores_returnsAllHighScoresFromDB() = runBlocking {
        addTwoHighScoresToDb()
        val allHighScores = highScoreDao.getAllScores().first()
        Assert.assertEquals(allHighScores[0],highScore1)
        Assert.assertEquals(allHighScores[1],highscore2)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        highScoreRoomDatabase.close()
    }
}