package ca.thenightcrew.supervinebros

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs

// K+
class GameFragment : Fragment() {
    private val args: GameFragmentArgs by navArgs()
    private val gameText: TextView by lazy { requireView().findViewById(R.id.gameText) }
    private val backButton: View by lazy { requireView().findViewById(R.id.game_back_button) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_game, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gameText.text = "Hello ${args.level}"
        backButton.setOnClickListener {
            val destination = GameFragmentDirections.actionGameFragmentToMenuFragment()
            view.findNavController().navigate(destination)
        }
    }
}