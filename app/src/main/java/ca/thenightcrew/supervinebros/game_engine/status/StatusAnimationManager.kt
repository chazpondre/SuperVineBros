package ca.thenightcrew.supervinebros.game_engine.status

import android.view.View
import android.widget.TextView
import ca.thenightcrew.supervinebros.animation.AnimationEngine
import ca.thenightcrew.supervinebros.animation.KeyTime
import ca.thenightcrew.supervinebros.animation.SimpleKeyFrame


/**
 * A class that helps manage the animation of display the end of level statuses
 */
internal class StatusAnimationManager {
    /**
     * Shows and animates the completed status on screen
     * @param animationEngine The animation engine used in this game
     * @param background The background used to fade and hide the game level
     * @param titleText The title text used to display the title of the status message
     * @param coinText The text used to represent the amount of coins a user has collected
     * @param timeText The text used to represent the amount of time elapsed
     * @param scoreText The text used to represent the total score of a user spanning all games
     */
    fun showCompletedStatus(
        animationEngine: AnimationEngine,
        background: View,
        titleText: TextView,
        coinText: TextView,
        timeText: TextView,
        scoreText: TextView
    ) {
        val backgroundFadeAnimationLength = 2
        animationEngine.addKeyframe(
            "Fade Background",
            backgroundFadeAnimationLength.toFloat(),
            object : SimpleKeyFrame(KeyTime(0f, backgroundFadeAnimationLength.toFloat()), true, 0) {
                override fun onKeyframe(frameNumber: Int, totalFramesCalled: Int) {
                    val opacity = covertFrameToSeconds(frameNumber) / backgroundFadeAnimationLength
                    background.alpha = opacity
                }
            })
        animationEngine.addKeyframe(
            "Title Text Animation", 3f,
            object : SimpleKeyFrame(KeyTime(2f, 3f), false, 0) {
                override fun onKeyframe(frameNumber: Int, totalFramesCalled: Int) {
                    titleText.visibility = View.VISIBLE
                }
            })
        animationEngine.addKeyframe(
            "Coin & Time Text Animation", 5f,
            object : SimpleKeyFrame(KeyTime(3f, 4f), false, 0) {
                override fun onKeyframe(frameNumber: Int, totalFramesCalled: Int) {
                    coinText.visibility = View.VISIBLE
                    timeText.visibility = View.VISIBLE
                }
            })
        animationEngine.addKeyframe(
            "ScoreKeeper Text Animation", 6f,
            object : SimpleKeyFrame(KeyTime(4f, 5f), false, 0) {
                override fun onKeyframe(frameNumber: Int, totalFramesCalled: Int) {
                    scoreText.visibility = View.VISIBLE
                }
            })
    }
}
