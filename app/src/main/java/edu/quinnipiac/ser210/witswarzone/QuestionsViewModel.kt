package edu.quinnipiac.ser210.witswarzone

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuestionsViewModel: ViewModel() {
    var questionsLiveData: MutableLiveData<TriviaQuestions?> = MutableLiveData()
    var triviaQuestions: TriviaQuestions = TriviaQuestions(ArrayList())
    // live data representation for summary value
    fun getCreateTextSummaryObserver(): MutableLiveData<TriviaQuestions?> {
        return questionsLiveData
    }

    fun setLiveData()
    {
        questionsLiveData.value = triviaQuestions
    }

    // gets summary from data class
    fun getQuestions(): List<Question>
    {
        return triviaQuestions.items
    }

    // calls API to generate summary from user's input text
    fun generateQuestions() {
        val retroService = ApiInterface.create()

        val call = retroService.getQuestions("general", 6)

        // handles API call on concurrent thread so as not to slow down UI
        call.enqueue(object : Callback<List<Question>> {
            override fun onFailure(call: Call<List<Question>>, t: Throwable) {
                questionsLiveData.postValue(null)
                println("FAILURE")
            }

            override fun onResponse(call: Call<List<Question>>, response: Response<List<Question>>) {
                if (response.isSuccessful) {
                    triviaQuestions.items = response.body() as List<Question>
                    questionsLiveData.postValue(triviaQuestions)
                    println("SUCCESS")
                    println("QUESTIONS: " + triviaQuestions.items)
                    println("SIZE: " + triviaQuestions.items.size)

                } else {
                    questionsLiveData.postValue(null)
                    println("FAILURE")
                }
            }
        })
    }
}
