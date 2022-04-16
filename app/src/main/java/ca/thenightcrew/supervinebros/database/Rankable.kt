package ca.thenightcrew.supervinebros.database

interface Rankable {
    suspend fun getTop10(): List<PlayerDateAmount>
}
