package edu.quinnipiac.ser210.witswarzone

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.SystemClock
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Chronometer
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.widget.RadioGroup
import android.widget.RadioButton
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import edu.quinnipiac.ser210.witswarzone.databinding.FragmentGameBinding

class GameFragment : Fragment()
{
    lateinit var viewModel: QuestionsViewModel
    lateinit var submitButton: Button
    lateinit var questionLabel: TextView
    lateinit var scoreLabel: TextView
    lateinit var questionNumLabel: TextView
    lateinit var userAnswer: EditText
    lateinit var timer: Chronometer
    lateinit var questions: ArrayList<Question>
    lateinit var pauseBtn: Button


    var userName: String = "Guest"
    var category: String = "general"
    var questionNum: Int = 1
    var answer: String = ""
    var score: Int = 0
    var length: Int = 10

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = arguments
        if (bundle == null) {
            Log.e("DetailFragment", "DetailFragment did not receive hero id")

            return
        }

        category = GameFragmentArgs.fromBundle(bundle).selectedCategory
        userName = GameFragmentArgs.fromBundle(bundle).userName
        length = GameFragmentArgs.fromBundle(bundle).length
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        _binding = FragmentGameBinding.inflate(inflater, container, false)

        initViewModel()
        generateQuestions()

        submitButton = _binding!!.submitButton
        questionLabel = _binding!!.questionLabel
        scoreLabel = _binding!!.scoreLabel
        userAnswer = _binding!!.userAnswer
        questionNumLabel = _binding!!.questionNumberLabel
        pauseBtn = _binding!!.pauseBtn
        timer = _binding!!.gameTimer
        timer.start()

        fun onPausePressed(state: Boolean) {
            if(state) {
                questionLabel.isVisible = true
                userAnswer.isVisible = true
                submitButton.isVisible = true
            } else {
                questionLabel.isVisible = false
                userAnswer.isVisible = false
                submitButton.isVisible = false
            }
        }

        var timerRunning = true
        var diff: Long = 0
        pauseBtn.setOnClickListener{
            if(timerRunning) {
                diff = timer.base - SystemClock.elapsedRealtime()
                timer.stop()
                pauseBtn.text = "▷"
                timerRunning = false
            } else {
                timer.base = SystemClock.elapsedRealtime() + diff
                timer.start()
                pauseBtn.text = "||"
                timerRunning = true
            }
            onPausePressed(timerRunning)
        }

        scoreLabel.text = "Score: $score"

        submitButton.setOnClickListener{

            if(questionNum < questions.size)
            {
                val nextQuestion: Question = questions[questionNum]
                val displayNum: Int = questionNum + 1
                val isCorrect: Boolean = userAnswer.text.toString().lowercase().trim()
                    .equals(answer)

                if (isCorrect)
                {
                    score++
                    scoreLabel.text = "Score: $score"
                    Toast.makeText(requireActivity(), "Correct!!", Toast.LENGTH_LONG).show()
                }
                else Toast.makeText(requireActivity(), "Incorrect, $answer was the correct answer.", Toast.LENGTH_LONG).show()

                questionLabel.text = nextQuestion.question
                questionNumLabel.text = "Question $displayNum out of ${questions.size}"
                answer = nextQuestion.answer.lowercase().trim()
                questionNum++
            }
            else if(!submitButton.text.equals("View Score"))
            {
                questionLabel.text = "Quiz Done!! Click button to view results."
                submitButton.text = "View Score"
                userAnswer.isVisible = false
            }
            else
            {
                val action = GameFragmentDirections.actionGameFragmentToResultsFragment(userName, score)
                this.findNavController().navigate(action)
            }
            userAnswer.setText("")
        }

        return binding.root
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
        viewModel.generateQuestions(category,length)
    }
}