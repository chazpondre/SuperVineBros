package ca.thenightcrew.supervinebros.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import ca.thenightcrew.supervinebros.R
import ca.thenightcrew.supervinebros.animation.AnimationEngine
import ca.thenightcrew.supervinebros.game_engine.gameSave
import ca.thenightcrew.supervinebros.game_engine.level.LevelEventHandler
import ca.thenightcrew.supervinebros.game_engine.level.SlideViewModel
import ca.thenightcrew.supervinebros.game_engine.sprite.SpriteFactory
import ca.thenightcrew.supervinebros.levels.appLevels
import com.google.android.material.button.MaterialButton

abstract class LevelBinderFragment : Fragment(), LevelEventHandler {
    private val args: GameFragmentArgs by navArgs()
    override val slideViewModel: SlideViewModel by viewModels()

    private val congratulations: View by lazy { requireView().findViewById(R.id.slide_congratulations_layout) }
    private val slideStartButton: MaterialButton by lazy { requireView().findViewById(R.id.slide_start_button) }
    override val welcomeScreen: View by lazy { requireView().findViewById(R.id.slide_welcome) }
    override val backButton: View by lazy { requireView().findViewById(R.id.game_back_button) }

    override val animationEngine = AnimationEngine()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_game, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("onViewCreated", "activity")
        super.onViewCreated(view, savedInstanceState)

        backButton.setOnClickListener {
            val destination = GameFragmentDirections.actionGameFragmentToMenuFragment()
            view.findNavController().navigate(destination)
        }

        setUpView(view)
        Log.d("onViewCreated", "after")
    }

    open fun setUpView(view: View) {
        Log.d("setUpView(view: View)", "before activity")
        if (activity != null) {
            //ViewModelProviders.of(activity).get(SlideViewModel::class.java)
            Log.d("setUpView(view: View)", "activity")
            slideViewModel.setUpView(view, requireContext(), SpriteFactory.mario(view))
            //  Level changes here
            slideViewModel.setLevels(listOf(appLevels[args.level].buildLevel(resources)))
            slideViewModel.subscribeToLevelEvents(viewLifecycleOwner, this)
        }
    }

    override fun onDetach() {
        animationEngine.kill()
        super.onDetach()
        saveGameInfo(onSaveGame());
    }

    private fun saveGameInfo(gameSave: gameSave) {}
    abstract fun onSaveGame(): gameSave
}