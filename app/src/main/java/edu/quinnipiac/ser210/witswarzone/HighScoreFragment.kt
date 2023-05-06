/*
    App:   WITSWARZONE
    Names: Emilio Cruz, William Siri
    Date: May 2023
 */

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
import edu.quinnipiac.ser210.witswarzone.databinding.FragmentHighScoreBinding

class HighScoreFragment : Fragment()
{
    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: ScoreAdapter

    private var _binding: FragmentHighScoreBinding? = null
    private val binding get() = _binding!!

    // initialize viewModel for database fetch
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
        _binding = FragmentHighScoreBinding.inflate(inflater, container, false)

        val clearButton = _binding!!.buttonClearScores
        clearButton.setOnClickListener{
            viewModel.deleteAllScores()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = _binding!!.recyclerview
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}