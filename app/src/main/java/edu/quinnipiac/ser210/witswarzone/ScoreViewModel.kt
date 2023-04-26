package edu.quinnipiac.ser210.witswarzone

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class ScoreViewModel(private val highScoreDao: HighScoreDao): ViewModel()
{
    val allScores: LiveData<List<HighScore>> = highScoreDao.getAllScores().asLiveData()

    fun addNewScore(name: String, score: String)
    {
        val newScore = getNewScoreEntry(name, score)
        insertScore(newScore)
    }

    fun deleteAllScores()
    {
        viewModelScope.launch {
            highScoreDao.deleteAllScores()
        }
    }

    private fun insertScore(newScore: HighScore)
    {
        viewModelScope.launch {
            highScoreDao.insert(newScore)
        }
    }

    private fun getNewScoreEntry(newName: String, newScore: String): HighScore
    {
        return(
                HighScore(
                    name = newName,
                    score = newScore.toInt()
                ))
    }

    fun isEntryValid(name: String, score: String): Boolean
    {
        return (name.isNotBlank() && score.isNotBlank())
    }
}

class ScoreViewModelFactory(private val highScoreDao: HighScoreDao): ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T
    {
        if(modelClass.isAssignableFrom(ScoreViewModel::class.java))
        {
            @Suppress("UNCHECKED_CAST")
            return ScoreViewModel(highScoreDao) as T
        }
        throw java.lang.IllegalArgumentException("Unknown ViewModel class")
    }
}