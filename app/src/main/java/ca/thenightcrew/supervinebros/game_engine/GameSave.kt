package ca.thenightcrew.supervinebros.game_engine

/**
 *
 * Class for getting the common game information such as the statistics, the level, the title
 * and the users points
 * The class takes in the title, score, level and points as parameters
 *
 */
class gameSave(
    val userScore: Int,
    val userLevel: Int,
    val coinsCollect: Int
) {

    override fun toString(): String {
        return "GameInfo{" +
                ", userScore=" + userScore +
                ", userLevel=" + userLevel +
                ", coinsCollect=" + coinsCollect +
                '}'
    }
}