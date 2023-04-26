package edu.quinnipiac.ser210.witswarzone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.fragment.findNavController

class HomeFragment : Fragment() {
    lateinit var editText: EditText
    lateinit var image: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        editText = view.findViewById(R.id.usernameInput)
        image = view.findViewById(R.id.imageView)

        image.setImageResource(R.drawable.wwlogo)

        var username = "Guest"

        val buttonGo = view.findViewById<Button>(R.id.button_go)
        buttonGo.setOnClickListener{
            if(editText.text.trim().isNotBlank()) {
                username = editText.text.toString()
                val action = HomeFragmentDirections.actionHomeFragmentToListFragment(username)
                this.findNavController().navigate(action)
            }
            else
                Toast.makeText(requireActivity(), "Please enter your name first or continue as guest",
                    Toast.LENGTH_LONG).show()
        }

        val buttonGuest = view.findViewById<Button>(R.id.btn_guest)
        buttonGuest.setOnClickListener{
            //Log.d("TAG", username)
            val action = HomeFragmentDirections.actionHomeFragmentToListFragment(username)
            this.findNavController().navigate(action)
        }

        return view
    }
}