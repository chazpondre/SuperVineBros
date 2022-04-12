package ca.thenightcrew.supervinebros.game_engine

// In Memory App State
object UserInfo {
    var currentUser : String? = null
    var stars: Int = 0
    var coins: Int = 0
    var score: Int = 0

    fun set(user: String, stars: Int = 0, coins: Int = 0, score: Int = 0) {
        currentUser = user
        this.stars = stars
        this.coins = coins
        this.score = score
    }

    fun clear(){
        currentUser = null
        this.stars = 0
        this.coins = 0
        this.score = 0
    }
}