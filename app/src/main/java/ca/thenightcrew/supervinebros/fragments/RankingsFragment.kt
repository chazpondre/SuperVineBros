package ca.thenightcrew.supervinebros.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ca.thenightcrew.supervinebros.R
import ca.thenightcrew.supervinebros.database.Rankable
import ca.thenightcrew.supervinebros.db
import ca.thenightcrew.supervinebros.game_engine.Utils
import ca.thenightcrew.supervinebros.levels.NoDiffer
import kotlinx.coroutines.runBlocking

val categories = listOf("Scores", "Stars", "Coins")

class RankingsFragment : Fragment() {
    private val backButton: View by lazy { requireView().findViewById(R.id.rank_back_button) }
    private val categoryButton: Button by lazy { requireView().findViewById(R.id.categoryButton) }
    private val recyclerView: RecyclerView by lazy { requireView().findViewById(R.id.rankings_recycler) }
    private val viewCategory: String get() = categories[categoryIndex]
    private lateinit var rankAdapter: RankAdapter

    private var categoryIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_rankings, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.configure()

        backButton.setOnClickListener {
            val action =
                RankingsFragmentDirections.actionRankingsFragmentToMenuFragment()
            requireView().findNavController().navigate(action)
        }

        categoryButton.setOnClickListener {
            categoryIndex = ++categoryIndex % categories.size
            categoryButton.text = categories[categoryIndex]
            updateScores()
        }
    }


    private fun RecyclerView.configure() {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        RankAdapter().also {
            rankAdapter = it
            adapter = it
            updateScores()
        }
    }

    private fun submitToRecycler(it: RankAdapter, rankList: List<Ranking>) {
        Utils.Threads.runOnMainThread(requireContext()) {
            it.submitList(rankList)
        }
    }


    private fun updateScores() {
        Utils.Threads.runOnDBThread {
            runBlocking {
                val rankList = db
                    .let { if (categoryIndex == 0) it.playerScores() else if (categoryIndex == 1) it.playerStars() else it.playerCoins() }
                    .getTop10().mapIndexed { index, playerDateAmount ->
                        Ranking(
                            index + 1,
                            viewCategory,
                            playerDateAmount.player,
                            playerDateAmount.amount
                        )
                    }
                submitToRecycler(rankAdapter, rankList)
            }
        }
    }
}


class RankAdapter : ListAdapter<Ranking, RankBinder>(NoDiffer<Ranking>()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankBinder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_ranking_item, parent, false)
        return RankBinder(layout)
    }

    override fun onBindViewHolder(binder: RankBinder, position: Int) {
        binder.bind(getItem(position))
    }
}

data class Ranking(val index: Int, val category: String, val name: String, val score: Int)

class RankBinder(item: View) : RecyclerView.ViewHolder(item) {
    fun bind(ranking: Ranking) {
        val playerId = ranking.name
        itemView.findViewById<View>(R.id.rankItem).setOnClickListener {
            val action =
                RankingsFragmentDirections.actionRankingsFragmentToDetailPlayerFragment(playerId)
            itemView.findNavController().navigate(action)
        }
        itemView.findViewById<TextView>(R.id.rank_num).text = ranking.index.toString()
        itemView.findViewById<TextView>(R.id.rankCategory).text = ranking.category
        itemView.findViewById<TextView>(R.id.rank_user).text = playerId
        itemView.findViewById<TextView>(R.id.rankScore).text = ranking.score.toString()
    }
}