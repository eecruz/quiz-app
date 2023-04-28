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

class ListFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: CategoryListAdapter
    lateinit var rGroup: RadioGroup

    var username: String = "Guest"
    var length: Int = 5

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
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        rGroup = view.findViewById(R.id.lengthRadioGroup)
        rGroup.setOnCheckedChangeListener {_,checkedId ->
            rGroup.check(checkedId)
            length = when (rGroup.checkedRadioButtonId) {
                R.id.radio5 -> { 5 }
                R.id.radio15 -> { 15 }
                else -> { 30 }
            }
            updateLength(view,length)
        }
        recyclerView = view.findViewById(R.id.recyclerview)
        recyclerAdapter = CategoryListAdapter(Navigation.findNavController(view), username, length)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = recyclerAdapter

        recyclerAdapter.setCategoryListItems()
    }

    private fun updateLength(view: View, newLength: Int) {
        recyclerAdapter = CategoryListAdapter(Navigation.findNavController(view), username, newLength)
    }
}