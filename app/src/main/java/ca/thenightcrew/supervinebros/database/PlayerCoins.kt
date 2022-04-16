package ca.thenightcrew.supervinebros.database

import androidx.room.*

@Entity(primaryKeys = ["playerId", "date"])
data class PlayerCoins(val playerId: String, val date: Int)

@Dao
interface PlayerCoinsDAO: Rankable {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(playerCoins: PlayerCoins)

    @Query("SELECT playerId, date, amount from PlayerCoins natural join " +
            "(SELECT date, amount FROM Coins ORDER BY amount DESC LIMIT 10) ORDER BY amount DESC")
    override suspend fun getTop10(): List<PlayerDateAmount>

    @Query("SELECT max(amount) FROM (SELECT * FROM Coins natural join (SELECT date FROM PlayerCoins WHERE playerId = :playerId))")
    fun getTopCoins(playerId: String): Int
}

