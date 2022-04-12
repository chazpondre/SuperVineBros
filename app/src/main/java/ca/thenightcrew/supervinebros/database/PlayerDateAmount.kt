package ca.thenightcrew.supervinebros.database

import androidx.room.ColumnInfo

/** Note: Not related to sql queries but how a query like [PlayerScores] queries will be parsed */
data class PlayerDateAmount(
    @ColumnInfo(name = "playerId") val player: String,
    @ColumnInfo(name = "date") val date: Int,
    @ColumnInfo(name = "amount") val amount: Int,
)