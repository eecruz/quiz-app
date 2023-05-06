/*
    App:   WITSWARZONE
    Names: Emilio Cruz, William Siri
    Date: May 2023
 */

package edu.quinnipiac.ser210.witswarzone

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// view model for question retrieval from API
class QuestionsViewModel: ViewModel() {
    var questionsLiveData: MutableLiveData<ArrayList<Question>?> = MutableLiveData()
    var triviaQuestions: TriviaQuestions = TriviaQuestions(ArrayList(1))

    init {
        val q = Question("", "Loading Question...", "")
        triviaQuestions.items.add(q)
    }

    // live data representation for summary value
    fun getQuestionsObserver(): MutableLiveData<ArrayList<Question>?> {
        return questionsLiveData
    }

    fun setLiveData()
    {
        questionsLiveData.value = triviaQuestions.items
    }

    // gets questions from data class
    fun getQuestions(): ArrayList<Question>
    {
        return triviaQuestions.items
    }

    // calls API to generate questions
    fun generateQuestions(category: String, length: Int) {
        val retroService = ApiInterface.create()

        val call = retroService.getQuestions(category, length)

        // handles API call on concurrent thread so as not to slow down UI
        call.enqueue(object : Callback<List<Question>> {
            override fun onFailure(call: Call<List<Question>>, t: Throwable) {
                questionsLiveData.postValue(null)
                println("FAILURE")
            }

            override fun onResponse(call: Call<List<Question>>, response: Response<List<Question>>) {
                if (response.isSuccessful) {
                    triviaQuestions.items = response.body() as ArrayList<Question>
                    questionsLiveData.postValue(triviaQuestions.items)
                    println("SUCCESS")

                } else {
                    questionsLiveData.postValue(null)
                    println("FAILURE")
                }
            }
        })
    }
}
