package ca.thenightcrew.supervinebros.database

import androidx.room.*

@Entity(primaryKeys = ["playerId", "date"])
data class PlayerScores(val playerId: String, val date: Int)

@Dao
interface PlayerScoresDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(playerScores: PlayerScores): Int

    @Query("SELECT playerId, date, amount from PlayerScores natural join (SELECT date, amount FROM Score ORDER BY amount DESC LIMIT 10)")
    suspend fun getTop10(): List<PlayerDateAmount>
}

