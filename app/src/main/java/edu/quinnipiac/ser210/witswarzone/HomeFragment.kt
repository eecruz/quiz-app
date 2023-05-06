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
import android.widget.EditText
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import edu.quinnipiac.ser210.witswarzone.databinding.FragmentHighScoreBinding
import edu.quinnipiac.ser210.witswarzone.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    lateinit var editText: EditText
    lateinit var image: ImageView

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        editText = _binding!!.usernameInput
        image = _binding!!.imageView

        image.setImageResource(R.drawable.wwlogo)

        var username = "Guest"

        val buttonGo = _binding!!.buttonGo
        buttonGo.setOnClickListener{
            // ensure name has been entered
            if(editText.text.trim().isNotBlank()) {
                username = editText.text.toString()
                val action = HomeFragmentDirections.actionHomeFragmentToListFragment(username)
                this.findNavController().navigate(action)
            }
            else
                Toast.makeText(requireActivity(), "Please enter your name first or continue as guest",
                    Toast.LENGTH_LONG).show()
        }

        // navigate to ListFragment passing default "Guest" username
        val buttonGuest = _binding!!.btnGuest
        buttonGuest.setOnClickListener{
            //Log.d("TAG", username)
            val action = HomeFragmentDirections.actionHomeFragmentToListFragment(username)
            this.findNavController().navigate(action)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}