package ca.thenightcrew.supervinebros.database

import androidx.room.*

@Entity
data class Stars(
    @PrimaryKey(autoGenerate = true) var date: Int,
    val amount: Int,
)

@Dao
interface StarsDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(stars: Stars)

    @Query("SELECT * FROM Stars ORDER BY amount DESC LIMIT 10")
    suspend fun getTop10(): List<Stars>
}
