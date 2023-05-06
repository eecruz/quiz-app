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
import android.widget.RadioGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.quinnipiac.ser210.witswarzone.databinding.FragmentHomeBinding
import edu.quinnipiac.ser210.witswarzone.databinding.FragmentListBinding

class ListFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: CategoryListAdapter
    lateinit var rGroup: RadioGroup

    var username: String = "Guest"
    var length: Int = 5

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = arguments
        if (bundle == null)
            return

        username = ListFragmentArgs.fromBundle(bundle).userName
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        // Inflate the layout for this fragment
        _binding = FragmentListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        // create and show quiz length buttons
        rGroup = _binding!!.lengthRadioGroup
        rGroup.setOnCheckedChangeListener {_,checkedId ->
            rGroup.check(checkedId)

            // sets length according to button
            length = when (rGroup.checkedRadioButtonId) {
                R.id.radio5 -> { 5 }
                R.id.radio15 -> { 15 }
                else -> { 30 }
            }
            updateLength(view,length)
        }
        recyclerView = _binding!!.recyclerview
        recyclerAdapter = CategoryListAdapter(Navigation.findNavController(view), username, length)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = recyclerAdapter

        recyclerAdapter.setCategoryListItems()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateLength(view: View, newLength: Int) {
        recyclerAdapter = CategoryListAdapter(Navigation.findNavController(view), username, newLength)
    }
}