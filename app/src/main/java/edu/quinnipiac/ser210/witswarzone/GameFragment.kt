package edu.quinnipiac.ser210.witswarzone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlin.random.Random

class GameFragment : Fragment()
{
    lateinit var viewModel: QuestionsViewModel
    lateinit var submitButton: Button
    lateinit var questionLabel: TextView
    lateinit var questions: ArrayList<Question>

    var index: Int = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val view = inflater.inflate(R.layout.fragment_game, container, false)

        initViewModel()
        generateQuestions()

        submitButton = view.findViewById(R.id.submit_button)
        questionLabel = view.findViewById(R.id.question_label)

        submitButton.setOnClickListener{
            if(index < questions.size)
            {
                questionLabel.text = questions[index].question
                index++
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setLiveData()

        // updates textview when questions are received from API
        viewModel.getQuestionsObserver().observe(viewLifecycleOwner) { newValue ->
            questions = newValue!!
            questionLabel.text = questions[0].question
        }
    }

    private fun initViewModel()
    {
        // preps data model class and sets observer for questions textview
        viewModel = ViewModelProvider(this).get(QuestionsViewModel::class.java)
        questions = viewModel.getQuestions()

        viewModel.getQuestionsObserver().observe(viewLifecycleOwner, Observer<ArrayList<Question>?> {
            if(it == null)
                Toast.makeText(requireActivity(), "An unknown error has occurred", Toast.LENGTH_LONG).show()
        })
    }

    private fun generateQuestions()
    {
        viewModel.generateQuestions()
    }
}