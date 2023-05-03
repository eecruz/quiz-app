package edu.quinnipiac.ser210.witswarzone

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import edu.quinnipiac.ser210.witswarzone.databinding.FragmentResultsBinding

class ResultsFragment : Fragment()
{
    var userName: String = "Guest"
    var score: Int = 0

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
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        // Inflate the layout for this fragment
        _binding = FragmentResultsBinding.inflate(inflater, container, false)

        val viewScoreButton = _binding!!.buttonViewScore
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