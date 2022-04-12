package ca.thenightcrew.supervinebros.database

import androidx.room.*

@Entity
data class Score (
    @PrimaryKey(autoGenerate = true) var date: Int,
    val amount: Int
)

@Dao
interface ScoreDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(score: Score)

    @Query("SELECT * FROM Score ORDER BY amount DESC LIMIT 10")
    suspend fun getTop10(): List<Coins>

}
