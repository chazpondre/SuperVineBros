package ca.thenightcrew.supervinebros.database

import androidx.room.*

@Entity
data class Coins (
    @PrimaryKey(autoGenerate = true) var date: Int = (System.currentTimeMillis() / 1000).toInt(),
    val amount: Int
)

@Dao
interface CoinsDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(coins: Coins)

    @Query("SELECT * FROM Coins ORDER BY amount DESC LIMIT 10")
    suspend fun getTop10(): List<Coins>
}
