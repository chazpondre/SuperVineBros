package ca.thenightcrew.supervinebros.game_engine.level.components.items.types

import android.widget.ImageView
import ca.thenightcrew.supervinebros.animation.AnimationEngine
import ca.thenightcrew.supervinebros.game_engine.level.SlideLevelManager
import ca.thenightcrew.supervinebros.game_engine.level.components.items.LevelItem
import ca.thenightcrew.supervinebros.game_engine.sprite.Sprite
import ca.thenightcrew.supervinebros.game_engine.status.ScoreKeeper


/**
 * A Bad item represents a item that when a sprite interacts with it has a negative effect on the
 * sprite and slows down the gameplay
 */
abstract class BadItem : LevelItemAnimator(), LevelItem {
    /** See [LevelItem.interact]  */
    override fun interact(
        sprite: Sprite,
        score: ScoreKeeper,
        levelManager: SlideLevelManager,
        itemImageView: ImageView,
        animationEngine: AnimationEngine
    ) {
        levelManager.resetSpeed()
        sprite.hurt(animationEngine)
    }
}
