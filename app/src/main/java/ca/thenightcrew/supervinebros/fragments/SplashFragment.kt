package ca.thenightcrew.supervinebros.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import ca.thenightcrew.supervinebros.R

class SplashFragment : Fragment() {
    private val nextButton: View by lazy { requireView().findViewById(R.id.nextSplashButton) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_splash, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nextButton.setOnClickListener {
            val action = SplashFragmentDirections.actionSplashFragmentToLoginFragment()
            view.findNavController().navigate(action)
        }
    }
}