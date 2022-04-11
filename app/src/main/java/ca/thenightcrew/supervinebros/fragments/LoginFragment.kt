package ca.thenightcrew.supervinebros.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import ca.thenightcrew.supervinebros.R


class LoginFragment : Fragment() {
    private val loginButton: View by lazy { requireView().findViewById(R.id.login_button) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_login, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginButton.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToMenuFragment()
            view.findNavController().navigate(action)
        }
    }
}
