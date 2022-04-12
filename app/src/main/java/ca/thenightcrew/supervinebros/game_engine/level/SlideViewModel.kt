package ca.thenightcrew.supervinebros.game_engine.level


import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import ca.thenightcrew.supervinebros.animation.AnimationEngine
import ca.thenightcrew.supervinebros.game_engine.Utils
import ca.thenightcrew.supervinebros.game_engine.level.components.SectionBuilder
import ca.thenightcrew.supervinebros.game_engine.sprite.Sprite
import ca.thenightcrew.supervinebros.game_engine.sprite.SpriteFactory
import ca.thenightcrew.supervinebros.game_engine.status.ScoreKeeper
import ca.thenightcrew.supervinebros.game_engine.status.StatusViewManager


/**
 * View Model Class that aids the SlideDown Fragment
 * [ca.carnivalgames.simplecarnival.fragments.games.slide_down.SlideDown]
 */
class SlideViewModel : ViewModel() {
    val scoreKeeper: ScoreKeeper by lazy { ScoreKeeper() }
    private lateinit var status: StatusViewManager
    private lateinit var levelManager: SlideLevelManager
    lateinit var sprite: Sprite
    private var level = 0

    /** Sets up some initial managers such as the Game ScoreKeeper, and the StatusViewManager
     * @param view
     * @param context
     */
    fun setUpView(view: View, context: Context, sprite: Sprite) {
        status = StatusViewManager(view)
        levelManager = LevelManager(context, view)
        this.sprite = sprite
    }

    fun setLevels(sectionBuilders: List<SectionBuilder>) {
        Utils.Threads.runOnBackgroundThread {
            levelManager.loadLevels(sectionBuilders)
            levelManager.levelStatusMutableLiveData.postValue(LevelStatusEnum.LEVELS_LOADED)
        }
    }

    fun subscribeToLevelEvents(lifecycleOwner: LifecycleOwner, levelEventHandler: LevelEventHandler) {
        levelManager.levelStatusObservable.observe(
            lifecycleOwner,
            levelManager.getObserverForLevelEvents(levelEventHandler)
        )
        levelManager
            .interactionEventObservable
            .observe(
                lifecycleOwner,
                levelManager.getObserverForInteractionEvent(levelEventHandler)
            )
    }

    fun startLevel(level: Int, animationEngine: AnimationEngine?) {
        this.level = level
        scoreKeeper.startTimer()
        sprite.showOnVineSprite(animationEngine!!)
        scoreKeeper.setLevel(level)
        levelManager.setLevel(level)!!.startVineAnimationSequence(animationEngine)
    }

    fun pauseLevel() {
        scoreKeeper.pauseTimer()
        levelManager.setPause(true)
    }

    fun upauseLevel() {
        scoreKeeper.unpauseTimer()
        levelManager.setPause(false)
    }

    fun moveSprite(direction: String, animationEngine: AnimationEngine?) {
        if (direction == "right") sprite.moveRight(animationEngine!!) else sprite.moveLeft(
            animationEngine!!
        )
    }

    fun handleLevelComplete(animationEngine: AnimationEngine?) {
        scoreKeeper.stopTimer()
        sprite.startEndLevelAnimation(animationEngine!!, (levelManager as LevelManager?)!!)
        status.showCompletedStatus(animationEngine, scoreKeeper, levelManager.levelWorstTime)
    }

    fun nextLevel(animationEngine: AnimationEngine?) {
        if (levelManager.hasLevel(level + 1)) {
            level++
            startLevel(level, animationEngine)
            levelManager.resetRecyclerPosition()
            sprite.setSpriteVisibilityAndInput()
            status.hideCompletedStatus()
        } else {
            levelManager.levelStatusMutableLiveData.setValue(LevelStatusEnum.GAME_COMPLETE)
        }
    }

    fun handleInteraction(interactionEvent: InteractionEvent, animationEngine: AnimationEngine?) {
        val position = sprite.position
        if (position.equals("LEFT", ignoreCase = true)) {
            val itemImageView = interactionEvent.getImageViewLeft()
            val levelItem = interactionEvent.levelItemLeft
            if (levelItem != null) levelItem.interact(
                sprite,
                scoreKeeper, levelManager, itemImageView, animationEngine!!
            )
        } else {
            val itemImageView = interactionEvent.getImageViewRight()
            val levelItem = interactionEvent.levelItemRight
            if (levelItem != null) levelItem.interact(
                sprite,
                scoreKeeper, levelManager, itemImageView, animationEngine!!
            )
        }
    }
}
