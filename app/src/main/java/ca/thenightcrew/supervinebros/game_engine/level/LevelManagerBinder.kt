package ca.thenightcrew.supervinebros.game_engine.level

import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import ca.thenightcrew.supervinebros.R
import ca.thenightcrew.supervinebros.game_engine.level.components.LevelAdapter
import ca.thenightcrew.supervinebros.game_engine.level.components.LevelRecyclerView
import ca.thenightcrew.supervinebros.game_engine.level.components.SectionBuilder


/**
 * The binder class which is responsible for binding any elements used in the LevelManager Class
 * [LevelManager]
 */
open class LevelManagerBinder(context: Context?, val view: View) {
    val levelStatusMutableLiveData: MutableLiveData<LevelStatusEnum> =
        MutableLiveData<LevelStatusEnum>()

    val interactionEventMutableLiveData = MutableLiveData<InteractionEvent>()
    val levelAnimationManager = LevelAnimationManager(levelStatusMutableLiveData)
    val levelAdapter = LevelAdapter()
    val levelLayoutManager = LinearLayoutManager(context)
    lateinit var levelDesigns: List<SectionBuilder>
    var levelNumber = 0

    val levelRecyclerView: LevelRecyclerView =
        view.findViewById<LevelRecyclerView>(R.id.slide_level_recycler_view).apply {
            adapter = levelAdapter
            layoutManager = levelLayoutManager
        }

    /**
     * Creates an live data observer that keeps track of of changes in the LevelStatus. For instance a
     * level can be completed, a new level can be loaded and the game can be completed
     *
     * @param levelEventHandler The event handler(observer) that will manage state changes of level
     * status
     * @return A new live data observer
     */
    fun getObserverForLevelEvents(
        levelEventHandler: LevelEventHandler
    ): Observer<LevelStatusEnum> {
        return Observer { levelStatus ->
            if (levelStatus === LevelStatusEnum.LEVEL_COMPLETE) {
                levelEventHandler.onLevelComplete()
            } else if (levelStatus === LevelStatusEnum.LEVEL_NEXT) {
                levelEventHandler.onNextLevel()
            }
            if (levelStatus === LevelStatusEnum.GAME_COMPLETE) {
                levelEventHandler.onGameComplete()
            }
            if (levelStatus === LevelStatusEnum.LEVELS_LOADED) {
                levelEventHandler.onLoadLevel()
            }
        }
    }

    /**
     * Creates an live data observer that keeps track of of interactions of sprites and level objects.
     *
     * @param levelEventHandler The event handler(observer) that will observer interaction events
     * @return A new live data observer that handles interactionEvents
     */
    fun getObserverForInteractionEvent(
        levelEventHandler: LevelEventHandler
    ): Observer<InteractionEvent> {
        return Observer { interactionEvent ->
            levelEventHandler.onInteraction(
                interactionEvent!!
            )
        }
    }
}