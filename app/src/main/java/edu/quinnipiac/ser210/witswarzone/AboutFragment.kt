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
import edu.quinnipiac.ser210.witswarzone.databinding.FragmentAboutBinding
import edu.quinnipiac.ser210.witswarzone.databinding.FragmentHomeBinding

class AboutFragment : Fragment()
{
    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        // Inflate the layout for this fragment
        _binding = FragmentAboutBinding.inflate(inflater, container, false)

        val emilioImage = _binding!!.emilioImage
        val billyImage = _binding!!.billyImage
        val emilioBio = _binding!!.emilioBio
        val billyBio = _binding!!.billyBio

        emilioImage.setImageResource(R.drawable.emilio_dev_pic)
        billyImage.setImageResource(R.drawable.billy_dev_pic)
        emilioBio.text = getString(R.string.emilio_bio)
        billyBio.text = getString(R.string.billy_bio)

        return binding.root
    }
}