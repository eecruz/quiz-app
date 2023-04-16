package edu.quinnipiac.ser210.witswarzone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import kotlin.random.Random

class GameFragment : Fragment()
{
    lateinit var viewModel: QuestionsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val view = inflater.inflate(R.layout.fragment_game, container, false)

        initViewModel()
        generateQuestions()

        val button = view.findViewById<Button>(R.id.button)
        val textview = view.findViewById<TextView>(R.id.textView)
        button.setOnClickListener{
            textview.text = viewModel.getQuestions().get(Random.nextInt(0, 6)).question
        }

        // Inflate the layout for this fragment
        return view
    }

    private fun initViewModel()
    {
        viewModel = ViewModelProvider(this)[QuestionsViewModel::class.java]
    }

    private fun generateQuestions()
    {
        viewModel.generateQuestions()
    }
}