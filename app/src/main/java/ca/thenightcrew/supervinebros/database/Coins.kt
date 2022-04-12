package ca.thenightcrew.supervinebros.database

import androidx.room.*

@Entity
data class Coins (
    @PrimaryKey(autoGenerate = true) var date: Int,
    val amount: Int
)

@Dao
interface CoinsDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(coins: Coins)

    @Query("SELECT * FROM Coins ORDER BY amount DESC LIMIT 10")
    suspend fun getTop10(): List<Coins>

//    // Set
//    @Query("UPDATE Coins SET allTime = :allTime WHERE id = :userId")
//    suspend fun setAllTime(userId: String, allTime: Int)
//
//    @Query("UPDATE Coins SET week = :week WHERE id = :userId")
//    suspend fun setWeek(userId: String, week: Int)
//
//    // Get
//    @Query("SELECT allTime FROM Coins WHERE id = :userId")
//    suspend fun getAllTime(userId: String): Int
//
//    @Query("SELECT week FROM Coins WHERE id = :userId")
//    suspend fun getWeek(userId: String): Int
//
//    @Query("SELECT allTime FROM Coins WHERE id = :userId ORDER BY allTime DESC LIMIT 10")
//    suspend fun getTop10AllTime(userId: String): Int
//
//    @Query("SELECT allTime FROM Coins WHERE id = :userId ORDER BY allTime DESC LIMIT 10")
//    suspend fun getTop10Week(userId: String): Int
//
//    // Predicate
//    @Query("SELECT count(id) FROM Coins WHERE id = :userId")
//    suspend fun userSaveExists(userId: String):Boolean
}
