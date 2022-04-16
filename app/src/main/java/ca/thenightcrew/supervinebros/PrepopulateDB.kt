package ca.thenightcrew.supervinebros

import ca.thenightcrew.supervinebros.database.*
import ca.thenightcrew.supervinebros.game_engine.Utils
import kotlinx.coroutines.runBlocking

fun prepopulateDBWithSampleData(){
    var uniqueTime = 0
    fun time(offsetDays: Int) = (1649782131 - offsetDays * 60 * 60 * 24) + ++uniqueTime

    val players = mutableListOf(
        Player("Gary Trent Jr", 0, 0, "111", 0),
        Player("Al Capone", 0, 0, "111", 0),
        Player("Luigi", 0, 0, "111", 0),
        Player("Yoshi", 0, 0, "111", 0),
        Player("Franklin Clinton", 0, 0, "111", 0),
        Player("Bowser", 0, 0, "111", 0),
        Player("Peaky Blinder", 0, 0, "111", 0),
        Player("Los Santos", 0, 0, "111", 0),
        Player("Duck Hunt", 0, 0, "111", 0),
        Player("DMX", 0, 0, "111", 0),
        Player("test", 0, 0, "111", 0),
    )

    val coins = mutableListOf(
        Coins(time(1), 3),
        Coins(time(1), 33),
        Coins(time(1), 12),
        Coins(time(1), 9),
        Coins(time(1), 7),
        Coins(time(1), 2),
        Coins(time(1), 1),
        Coins(time(0), 4),
        Coins(time(0), 8),
        Coins(time(0), 6),
        Coins(time(0), 60),
        Coins(time(0), 1000),
    )

    val scores = mutableListOf(
        Score(time(1), 3000),
        Score(time(1), 3302),
        Score(time(1), 12234),
        Score(time(1), 952),
        Score(time(1), 7345),
        Score(time(0), 2345),
        Score(time(0), 13),
        Score(time(0), 48658),
        Score(time(0), 880),
        Score(time(1), 6),
        Score(time(1), 698),
        Score(time(0), 2346),
    )

    val stars = mutableListOf(
        Stars(time(1), 3),
        Stars(time(1), 32),
        Stars(time(1), 1),
        Stars(time(1), 52),
        Stars(time(1), 5),
        Stars(time(0), 4),
        Stars(time(0), 13),
        Stars(time(0), 48),
        Stars(time(0), 8),
        Stars(time(1), 6),
        Stars(time(1), 7),
        Stars(time(0), 2),
    )


    val coinsPlayerIndices = mutableListOf(0, 2, 4, 6, 8, 3, 1, 5, 7, 9, 1, 2)
    val scorePlayerIndices = mutableListOf(0, 2, 4, 6, 8, 3, 1, 5, 7, 9, 1, 2)
    val starsPlayerIndices = mutableListOf(2, 2, 5, 6, 4, 3, 1, 10, 7, 9, 1, 2)

    val playerCoins = mutableListOf<PlayerCoins>()
    coinsPlayerIndices.forEachIndexed { index, playerIndex ->
        val playerID = players[playerIndex].id
        val mappedID = coins[index].date
        playerCoins.add(PlayerCoins(playerID, mappedID))
    }

    val playerStars = mutableListOf<PlayerStars>()
    starsPlayerIndices.forEachIndexed { index, playerIndex ->
        val playerID = players[playerIndex].id
        val mappedID = stars[index].date
        playerStars.add(PlayerStars(playerID, mappedID))
    }

    val playerScores = mutableListOf<PlayerScores>()
    scorePlayerIndices.forEachIndexed { index, playerIndex ->
        val playerID = players[playerIndex].id
        val mappedID = scores[index].date
        playerScores.add(PlayerScores(playerID, mappedID))
    }

    Utils.Threads.runOnDBThread {
        runBlocking {
            players.forEach { db.player().add(it) }
            coins.forEach { db.coins().add(it) }
            stars.forEach { db.stars().add(it) }
            scores.forEach { db.score().add(it) }
            playerScores.forEach { db.playerScores().add(it) }
            playerStars.forEach { db.playerStars().add(it) }
            playerCoins.forEach { db.playerCoins().add(it) }
        }
    }
}