package ca.thenightcrew.supervinebros.database

import androidx.room.*

@Entity(primaryKeys = ["playerId", "date"])
data class PlayerCoins(val playerId: String, val date: Int)

@Dao
interface PlayerCoinsDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(playerCoins: PlayerCoins): Int

    @Query("SELECT playerId, date, amount from PlayerCoins natural join (SELECT date, amount FROM Score ORDER BY amount DESC LIMIT 10)")
    suspend fun getTop10(): List<PlayerDateAmount>
}

