package ca.thenightcrew.supervinebros.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.NavArgs
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import ca.thenightcrew.supervinebros.R
import ca.thenightcrew.supervinebros.db
import ca.thenightcrew.supervinebros.game_engine.Utils
import kotlinx.coroutines.runBlocking

class DetailPlayerFragment : Fragment() {
    private val playerName: TextView by lazy { requireView().findViewById(R.id.detail_player_name) }
    val stars: TextView by lazy { requireView().findViewById(R.id.detailStars) }
    val score: TextView by lazy { requireView().findViewById(R.id.detailScore) }
    val coins: TextView by lazy { requireView().findViewById(R.id.detailCoins) }
    private val backButton: View by lazy { requireView().findViewById(R.id.detail_back_button) }
    private val args: DetailPlayerFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_detail_player, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backButton.setOnClickListener {
            val action =
                DetailPlayerFragmentDirections.actionDetailPlayerFragmentToRankingsFragment()
            requireView().findNavController().navigate(action)
        }

        val userId = args.playerID
        playerName.text = userId

        Utils.Threads.runOnDBThread {
            runBlocking {
                val topScore = db.playerScores().getTopScore(userId)
                val topCoins = db.playerCoins().getTopCoins(userId)
                val topStars = db.playerStars().getTopStars(userId)

                Utils.Threads.runOnMainThread(view.context){
                    stars.text = topStars.toString()
                    coins.text = topCoins.toString()
                    score.text = topScore.toString()
                }
            }
        }
    }
}