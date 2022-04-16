package ca.thenightcrew.supervinebros.database

import androidx.room.*

@Entity(primaryKeys = ["playerId", "date"])
data class PlayerScores(val playerId: String, val date: Int)

@Dao
interface PlayerScoresDAO : Rankable {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(playerScores: PlayerScores)

    @Query(
        "SELECT playerId, date, amount FROM PlayerScores natural join " +
                "(SELECT date, amount FROM Score ORDER BY amount DESC LIMIT 10) ORDER BY amount DESC"
    )
    override suspend fun getTop10(): List<PlayerDateAmount>

    @Query("SELECT max(amount) FROM (SELECT * FROM Score natural join (SELECT date FROM PlayerScores WHERE playerId = :playerId))")
    fun getTopScore(playerId: String): Int
}

