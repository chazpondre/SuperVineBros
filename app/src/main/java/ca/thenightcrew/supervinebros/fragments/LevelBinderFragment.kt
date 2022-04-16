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
import ca.thenightcrew.supervinebros.database.Coins
import ca.thenightcrew.supervinebros.database.PlayerCoins
import ca.thenightcrew.supervinebros.database.PlayerScores
import ca.thenightcrew.supervinebros.database.Score
import ca.thenightcrew.supervinebros.db
import ca.thenightcrew.supervinebros.game_engine.AppInfo
import ca.thenightcrew.supervinebros.game_engine.Utils
import ca.thenightcrew.supervinebros.game_engine.gameSave
import ca.thenightcrew.supervinebros.game_engine.level.LevelEventHandler
import ca.thenightcrew.supervinebros.game_engine.level.SlideViewModel
import ca.thenightcrew.supervinebros.game_engine.sprite.SpriteFactory
import ca.thenightcrew.supervinebros.levels.appLevels
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.runBlocking

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

            if (AppInfo.player != null) {
                val player = AppInfo.player!!
                player.lastPlayed = args.level
                Utils.Threads.runOnDBThread {
                    runBlocking {
                        db.player().setLastPlayed(player.id, args.level)
                    }
                }
            }
        }
    }

    override fun onDetach() {
        save()
        animationEngine.kill()
        super.onDetach()
        //saveGameInfo(onSaveGame());
    }

    private fun saveGameInfo(gameSave: gameSave) {}

    override fun save() {
        Log.d("Saving PLayer Stats", "levels unlocked")
        val scoreKeeper = slideViewModel.scoreKeeper
        val coinsCollected = scoreKeeper.score
        val scoreCollected = scoreKeeper.totalScore

        val player = AppInfo.player ?: return
        player.levelsUnlocked++

        Utils.Threads.runOnDBThread {
            runBlocking {
                val coins = Coins(amount = coinsCollected)
                val score = Score(amount = scoreCollected.toInt())
                db.player().setLevelsUnlock(player.id, player.levelsUnlocked)
                db.coins().add(coins)
                db.playerCoins().add(PlayerCoins(player.id, coins.date))
                db.score().add(score)
                db.playerScores().add(PlayerScores(player.id, score.date))
                Log.d("Saving PLayer Stats", "levels unlocked = ${player.levelsUnlocked}")
            }
        }
    }
}