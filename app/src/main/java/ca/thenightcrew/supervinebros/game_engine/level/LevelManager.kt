package ca.thenightcrew.supervinebros.game_engine.level

import android.content.Context
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ca.thenightcrew.supervinebros.animation.AnimationEngine
import ca.thenightcrew.supervinebros.game_engine.level.components.LevelSegment
import ca.thenightcrew.supervinebros.game_engine.level.components.SectionBuilder


class LevelManager(context: Context, view: View) : LevelManagerBinder(context, view),
    SlideLevelManager {
    override fun setLevel(levelNumber: Int): SlideLevelManager {
        this.levelNumber = levelNumber
        if (levelNumber < levelDesigns.size) levelAdapter.submitList(levelDesigns[levelNumber])
        return this
    }

    override fun loadLevels(levelDesigns: List<SectionBuilder>) {
        this.levelDesigns = levelDesigns
    }

    /**
     * Delegated Animation work to [LevelAnimationManager.onLevelStart]
     *
     * @param animationEngine The animation api used to render animations for the game
     */
    override fun startVineAnimationSequence(animationEngine: AnimationEngine) {
        levelAnimationManager.onLevelStart(
            animationEngine,
            levelRecyclerView,
            levelLayoutManager,
            levelDesigns[levelNumber],
            interactionEventMutableLiveData
        )
    }

    override fun setPause(isPaused: Boolean) {
        levelAnimationManager.setPaused(isPaused)
    }

    override fun startEndLevelSequence(animationEngine: AnimationEngine) {
        levelAnimationManager.onLevelEnd(animationEngine, view)
    }

    override val levelStatusObservable: LiveData<LevelStatusEnum>
        get() = levelStatusMutableLiveData
    override val interactionEventObservable: LiveData<InteractionEvent>
        get() = interactionEventMutableLiveData
//    override val levelStatusMutableObservable: MutableLiveData<LevelStatusEnum>
//        get() = levelStatusMutableObservable

    override fun resetSpeed() {
        levelAnimationManager.resetSpeed()
    }

    override val levelWorstTime: Int
        get() = levelDesigns[levelNumber].levelWorstTime

    override fun hasLevel(i: Int): Boolean {
        return i < levelDesigns.size
    }

    override fun resetRecyclerPosition() {
        levelRecyclerView.scrollTo(0, 0)
    }

//    companion object {
//        fun createManager(context: Context, view: View): SlideLevelManager {
//            return LevelManager(context, view)
//        }
//    }
}
