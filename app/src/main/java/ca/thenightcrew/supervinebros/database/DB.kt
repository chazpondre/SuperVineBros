package ca.thenightcrew.supervinebros.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Coins::class, Player::class, Score::class, Stars::class, PlayerScores::class, PlayerCoins::class, PlayerStars::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun player(): PlayerDAO
    abstract fun coins(): CoinsDAO
    abstract fun score(): ScoreDAO
    abstract fun stars(): StarsDAO
    abstract fun playerScores(): PlayerScoresDAO
    abstract fun playerStars(): PlayerStarsDAO
    abstract fun playerCoins(): PlayerCoinsDAO
}

