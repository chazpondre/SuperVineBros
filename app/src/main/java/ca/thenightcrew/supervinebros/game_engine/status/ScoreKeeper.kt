package ca.thenightcrew.supervinebros.game_engine.status


/**
 * A class focused on the management of scores and times across all levels of the game
 */
class ScoreKeeper {
    private val scoresPerLevel: ArrayList<Int> = ArrayList()
    private var level = 0
    private var levelStartTime: Long = 0
    private var levelEndTime: Long = 0
    private var extraTime: Long = 0

    /**
     * @return The total score across all levels
     */
    var totalScore: Long = 0
        private set
    private var isPaused = false

    /**
     * Increases the score for the current level
     * @param i The value amount the score will be increased
     */
    fun increaseBy(i: Int) {
        val newScore = scoresPerLevel[level] + i
        scoresPerLevel[level] = newScore
    }

    /**
     * Sets the current level that will be used in the calculation of time and scores
     * @param level The current level the user is on
     */
    fun setLevel(level: Int) {
        this.level = level
        scoresPerLevel.add(level, 0)
    }

    /** @return The current score for the current level
     */
    val score: Int
        get() = if (level < scoresPerLevel.size) scoresPerLevel[level] else 0

    fun updateTotalScore(levelWorstTimeLength: Int) {
        val timeDifference = levelWorstTimeLength - finalTimeInSeconds
        totalScore += (score * timeDifference).toLong()
    }

    /**
     * Stores the start time of a game
     */
    fun startTimer() {
        if (!isPaused) levelStartTime = System.currentTimeMillis()
    }

    /**
     * Stores the time the game ended
     */
    fun stopTimer() {
        if (!isPaused) levelEndTime = System.currentTimeMillis()
    }

    /**
     * Calculates the time the game ended excluding an pauses to the game
     * @return The time the user took to play through the level
     *
     */
    val finalTimeInSeconds: Int
        get() = ((levelEndTime - levelStartTime + extraTime) / 1000).toInt()

    /**
     * @return The current time that has elapsed in a level excluding game pauses
     */
    val timeElapsed: Int
        get() = ((System.currentTimeMillis() - levelStartTime + extraTime) % 1000).toInt()

    /**
     * Excludes times elapsed after this method is called
     */
    fun pauseTimer() {
        isPaused = true
        extraTime += levelStartTime - System.currentTimeMillis()
    }

    /**
     * Resumes any time that previously elapsed + any new time
     */
    fun unpauseTimer() {
        isPaused = false
        startTimer()
    }

    fun getLevel(): Int {
        return level
    }

}
