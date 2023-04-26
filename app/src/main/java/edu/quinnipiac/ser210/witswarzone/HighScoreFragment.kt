package edu.quinnipiac.ser210.witswarzone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HighScoreFragment : Fragment()
{
    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: ScoreAdapter

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
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_high_score, container, false)

        val clearButton = view.findViewById<Button>(R.id.button_clearScores)
        clearButton.setOnClickListener{
            viewModel.deleteAllScores()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerview)
        recyclerAdapter = ScoreAdapter()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = recyclerAdapter
        viewModel.allScores.observe(this.viewLifecycleOwner) { items ->
            items.let {
                recyclerAdapter.submitList(it)
                recyclerAdapter.setScores(viewModel.allScores.value)
            }
        }
    }
}