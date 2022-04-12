package ca.thenightcrew.supervinebros.game_engine.status


import android.view.View
import android.widget.TextView
import ca.thenightcrew.supervinebros.R
import ca.thenightcrew.supervinebros.animation.AnimationEngine


/** Helps organize the status menus that helps display games scores  */
class StatusViewManager(view: View) {
    private val animator: StatusAnimationManager = StatusAnimationManager()
    private val background: View
    private val statusText: TextView
    private val coinText: TextView
    private val timeText: TextView
    private val scoreText: TextView

    init {
        statusText = view.findViewById(R.id.slide_title_text)
        coinText = view.findViewById(R.id.slide_coins_status_text)
        timeText = view.findViewById(R.id.slide_time_status_text)
        scoreText = view.findViewById(R.id.slide_score_status_text)
        background = view.findViewById(R.id.black_background)
    }

    /**
     * Shows the status menu which displays the user scores such as coins collected and Time elapsed.
     * Helps animated the views when loaded
     *
     * @param animationEngine The animations engine given by the main fragment of the game that aids
     * in element animations
     * @param score The ScoreKeeper object that helps keep track of scores
     * @param levelWorstTime The worst time that can possible be attained in a level as set by the
     * level designer. This help calculate how well a user has done with regards to time
     */
    fun showCompletedStatus(
        animationEngine: AnimationEngine, score: ScoreKeeper, levelWorstTime: Int
    ) {
        statusText.setText(R.string.course_cleared_title)
        score.updateTotalScore(levelWorstTime)
        val coinString = "Coins = " + score.score
        val timeString = "Time = " + score.finalTimeInSeconds
        val scoreString = "Score = " + score.totalScore
        coinText.text = coinString
        timeText.text = timeString
        scoreText.text = scoreString
        animator.showCompletedStatus(
            animationEngine, background, statusText, coinText, timeText, scoreText
        )
    }

    /** Hides all of the status info displayed on the screen  */
    fun hideCompletedStatus() {
        background.alpha = 0f
        statusText.visibility = View.GONE
        coinText.visibility = View.GONE
        timeText.visibility = View.GONE
        scoreText.visibility = View.GONE
    }


}
