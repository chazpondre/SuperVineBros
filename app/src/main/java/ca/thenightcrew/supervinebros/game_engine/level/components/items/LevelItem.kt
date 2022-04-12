package ca.thenightcrew.supervinebros.game_engine.level.components.items

import android.widget.ImageView
import ca.thenightcrew.supervinebros.animation.AnimationEngine
import ca.thenightcrew.supervinebros.game_engine.level.SlideLevelManager
import ca.thenightcrew.supervinebros.game_engine.sprite.Sprite
import ca.thenightcrew.supervinebros.game_engine.status.ScoreKeeper


/**
 * The interface representing level items that will be generated in each segment of the recyclerView
 */
interface LevelItem {
    /**
     * This method is called when a level item has been interacted with. If the level affects the
     * sprite it can call methods directly on the sprite. Each LevelItem can have the possibility of
     * affecting the sprite, score, level elements such as speed
     *
     * @param sprite The sprite of which is interacting with this LevelItem
     * @param score The scoreKeeper object for this game
     * @param levelManager The current level Manager for this game
     * @param itemImageView The current ImageView that represents this item
     * @param animationEngine The animation engine used in this game
     */
    fun interact(
        sprite: Sprite,
        score: ScoreKeeper,
        levelManager: SlideLevelManager,
        itemImageView: ImageView,
        animationEngine: AnimationEngine
    )

    val resourceIDs: IntArray?
    val size: Int
    val nextResourceID: Int
}
