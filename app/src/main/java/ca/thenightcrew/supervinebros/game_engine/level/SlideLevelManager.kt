package ca.thenightcrew.supervinebros.game_engine.level

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import ca.thenightcrew.supervinebros.animation.AnimationEngine
import ca.thenightcrew.supervinebros.game_engine.level.components.SectionBuilder

interface SlideLevelManager {
    fun setLevel(levelNumber: Int): SlideLevelManager?
    fun loadLevels(levelDesigns: List<SectionBuilder>)
    fun startVineAnimationSequence(animationEngine: AnimationEngine)
    fun setPause(isPaused: Boolean)
    fun startEndLevelSequence(animationEngine: AnimationEngine)

    val levelStatusObservable: LiveData<LevelStatusEnum>
    val levelStatusMutableLiveData: MutableLiveData<LevelStatusEnum>
    val interactionEventObservable: LiveData<InteractionEvent>

    fun getObserverForInteractionEvent(levelEventHandler: LevelEventHandler): Observer<InteractionEvent>
    fun getObserverForLevelEvents(levelEventHandler: LevelEventHandler): Observer<LevelStatusEnum>

    fun resetSpeed()
    val levelWorstTime: Int

    fun hasLevel(i: Int): Boolean
    fun resetRecyclerPosition()
}
