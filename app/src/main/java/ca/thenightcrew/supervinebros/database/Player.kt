package ca.thenightcrew.supervinebros.database

import androidx.room.*

@Entity
data class Player (
    @PrimaryKey var id: String,
    val lastPlayed: Int,
    val levelsUnlocked: Int,
    val password: String,
    val lives: Int
)

@Dao
interface PlayerDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(player: Player)

    // Set
    @Query("UPDATE Player SET lastPlayed = :lastPlayed WHERE id = :userId")
    suspend fun setLastPlayed(userId: String, lastPlayed: Int)

    @Query("UPDATE Player SET lastPlayed = :lastPlayed WHERE id = :userId")
    suspend fun setLevelsUnlock(userId: String, lastPlayed: Int)

    @Query("UPDATE Player SET lives = :lives WHERE id = :userId")
    suspend fun setLives(userId: String, lives: Int)

    // Get
    @Query("SELECT lastPlayed FROM Player where id = :userId")
    suspend fun getLastPlayed(userId: String): Int

    @Query("SELECT lives FROM Player where id = :userId")
    suspend fun getLevelsUnlocked(userId: String): Int

    @Query("SELECT lives FROM Player where id = :userId")
    suspend fun getLives(userId: String): Int

    // Predicate
    @Query("SELECT count(id) FROM Player where id = :userId")
    suspend fun userExists(userId: String): Boolean

    @Query("SELECT * FROM Player where id = :userId and password = :password")
    suspend fun login(userId: String, password: String): Player?
}

