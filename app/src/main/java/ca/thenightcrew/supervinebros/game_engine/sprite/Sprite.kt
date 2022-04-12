package ca.thenightcrew.supervinebros.game_engine.sprite

import android.view.View
import ca.thenightcrew.supervinebros.animation.AnimationEngine
import ca.thenightcrew.supervinebros.game_engine.level.LevelManager


/** A class representing all possible interaction that can occurs with a Sprite  */
class Sprite internal constructor(view: View, resource: SpriteResource) : SpriteBinder(view, resource) {
    private var userInputIsDisabled = false

    /**
     * Moves the sprite left when the game is active
     *
     * @param animationEngine The animation engine used in this game
     */
    fun moveLeft(animationEngine: AnimationEngine) {
        if (userInputIsDisabled) return
        val hasMoved: Boolean = positionManager.positionSprite("LEFT", vineComponents)
        if (hasMoved) animationManager.onMoveToSideAnimation(
            animationEngine,
            vineComponents,
            onTopOfVine
        )
    }

    /**
     * Moves the sprite right when the game is active
     *
     * @param animationEngine The animation engine used in this game
     */
    fun moveRight(animationEngine: AnimationEngine) {
        if (userInputIsDisabled) return
        val hasMoved: Boolean = positionManager.positionSprite("RIGHT", vineComponents)
        if (hasMoved) animationManager.onMoveToSideAnimation(
            animationEngine,
            vineComponents,
            onTopOfVine
        )
    }

    /**
     * Begins the Sprite animation that makes the sprite like like it is descending the vine
     *
     * @param animationEngine  The animation engine used in this game
     */
    fun showOnVineSprite(animationEngine: AnimationEngine) {
        animationManager.onVineAnimation(animationEngine, legs, hands, blink)
    }

    /** @return The position of the sprite which can either be left or right
     */
    val position: String
        get() = positionManager.getPosition()

    /**
     * Delegates the animation the the animation manager that shows the end level sequence
     *
     * @param animationEngine The animation engine used in this game
     * @param levelManager The LevelManager used in this game
     */
    fun startEndLevelAnimation(animationEngine: AnimationEngine, levelManager: LevelManager) {
        userInputIsDisabled = true
        animationManager.endLevelAnimation(
            animationEngine,
            vineComponents,
            walkComponents,
            peace,
            onTopOfVine,
            levelManager.levelStatusMutableLiveData
        )
    }

    /**
     * Delegates the animation for when the sprite interact with an object that hurts it
     * @param animationEngine The animation engine used in this game
     */
    fun hurt(animationEngine: AnimationEngine) {
        animationManager.hurtAnimation(animationEngine, vineComponents, hurt, onTopOfVine)
    }

    /**
     * Makes the sprite visible and user interactable
     */
    fun setSpriteVisibilityAndInput() {
        userInputIsDisabled = false
        for (vineComponent in vineComponents) {
            vineComponent.setVisibility(View.VISIBLE)
        }
    }
}
