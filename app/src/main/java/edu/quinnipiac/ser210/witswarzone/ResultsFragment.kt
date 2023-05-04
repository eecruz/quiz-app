package edu.quinnipiac.ser210.witswarzone

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import edu.quinnipiac.ser210.witswarzone.databinding.FragmentResultsBinding

class ResultsFragment : Fragment()
{
    var userName: String = "Guest"
    var score: Int = 0
    var totalQuestions: Int = 0
    var time: String = ""

    private var _binding: FragmentResultsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ScoreViewModel by activityViewModels {
        ScoreViewModelFactory(
            (activity?.application as HighScoreApplication).database.highScoreDao()
        ) }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        val bundle = arguments

        if (bundle == null)
            return

        userName = ResultsFragmentArgs.fromBundle(bundle).userName
        score = ResultsFragmentArgs.fromBundle(bundle).score
        totalQuestions = ResultsFragmentArgs.fromBundle(bundle).totalQuestions
        time = ResultsFragmentArgs.fromBundle(bundle).time
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        // Inflate the layout for this fragment
        _binding = FragmentResultsBinding.inflate(inflater, container, false)

        val viewScoreButton = _binding!!.buttonViewScore
        val homeButton = _binding!!.buttonHome
        val timeLabel: TextView = _binding!!.timeResultsLabel
        val scoreLabel: TextView = _binding!!.scoreResultsLabel
        val medal: ImageView = _binding!!.medalImage

        timeLabel.text = "Time: $time"
        scoreLabel.text = "Score: $score/$totalQuestions"

        if(score.toDouble() / totalQuestions > 0.85)
            medal.setImageResource(R.drawable.gold_medal)
        else if(score.toDouble() / totalQuestions > 0.70)
            medal.setImageResource(R.drawable.silver_medal)
        else
            medal.setImageResource(R.drawable.bronze_medal)

        homeButton.setOnClickListener {
            val action = ResultsFragmentDirections.actionResultsFragmentToHomeFragment()
            this.findNavController().navigate(action)
        }

        viewScoreButton.setOnClickListener{
            addNewItem()
            val action = ResultsFragmentDirections.actionResultsFragmentToHighScoreFragment()
            this.findNavController().navigate(action)
        }
        val newQuizButton = _binding!!.buttonNewQuiz
        newQuizButton.setOnClickListener{
            addNewItem()
            val action = ResultsFragmentDirections.actionResultsFragmentToListFragment(userName)
            this.findNavController().navigate(action)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun isEntryValid(): Boolean
    {
        return (viewModel.isEntryValid(userName, score.toString()) && score != 0)
    }

//    private fun getScoreList(): List<HighScore>
//    {
//        return (viewModel.allScores)
//    }

    private fun addNewItem()
    {
        if(isEntryValid())
            viewModel.addNewScore(userName, score.toString())
    }
}