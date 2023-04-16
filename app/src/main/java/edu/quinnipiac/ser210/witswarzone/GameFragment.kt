package edu.quinnipiac.ser210.witswarzone

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class GameFragment : Fragment()
{
    lateinit var viewModel: QuestionsViewModel
    lateinit var submitButton: Button
    lateinit var questionLabel: TextView
    lateinit var scoreLabel: TextView
    lateinit var questionNumLabel: TextView
    lateinit var userAnswer: EditText
    lateinit var questions: ArrayList<Question>


    var questionNum: Int = 1
    var answer: String = ""
    var score: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val view = inflater.inflate(R.layout.fragment_game, container, false)

        initViewModel()
        generateQuestions()

        submitButton = view.findViewById(R.id.submit_button)
        questionLabel = view.findViewById(R.id.question_label)
        scoreLabel = view.findViewById(R.id.score_label)
        userAnswer = view.findViewById(R.id.userAnswer)
        questionNumLabel = view.findViewById(R.id.question_number_label)

        scoreLabel.text = "Score: $score"

        submitButton.setOnClickListener{

            if(questionNum < questions.size)
            {
                val nextQuestion: Question = questions[questionNum]
                val displayNum: Int = questionNum + 1
                val isCorrect: Boolean = userAnswer.text.toString().lowercase().trim()
                    .equals(answer)

                questionLabel.text = nextQuestion.question
                questionNumLabel.text = "Question $displayNum out of ${questions.size}"
                answer = nextQuestion.answer.lowercase().trim()
                questionNum++

                if (isCorrect)
                {
                    score++
                    scoreLabel.text = "Score: $score"
                }
            }
            userAnswer.setText("")
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
            questionNumLabel.text = "Question 1 out of ${questions.size}"
            answer = questions[0].answer.lowercase().trim()
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