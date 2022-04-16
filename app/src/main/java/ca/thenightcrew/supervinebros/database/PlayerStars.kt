package ca.thenightcrew.supervinebros.database

import androidx.room.*

@Entity(primaryKeys = ["playerId", "date"])
data class PlayerStars(val playerId: String, val date: Int)

@Dao
interface PlayerStarsDAO: Rankable {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(playerStars: PlayerStars)

    @Query("SELECT playerId, date, amount from PlayerStars natural join " +
            "(SELECT date, amount FROM Stars ORDER BY amount DESC LIMIT 10) ORDER BY amount DESC")
    override suspend fun getTop10(): List<PlayerDateAmount>

    @Query("SELECT max(amount) FROM (SELECT * FROM Stars natural join (SELECT date FROM PlayerStars WHERE playerId = :playerId))")
    fun getTopStars(playerId: String): Int
}