package ca.thenightcrew.supervinebros.database

import androidx.room.*

@Entity(primaryKeys = ["playerId", "date"])
data class PlayerStars(val playerId: String, val date: Int)

@Dao
interface PlayerStarsDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(playerStars: PlayerStars): Int

    @Query("SELECT playerId, date, amount from PlayerStars natural join (SELECT date, amount FROM Score ORDER BY amount DESC LIMIT 10)")
    suspend fun getTop10(): List<PlayerDateAmount>
}