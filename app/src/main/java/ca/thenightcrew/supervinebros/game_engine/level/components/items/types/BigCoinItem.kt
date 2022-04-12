package ca.thenightcrew.supervinebros.game_engine.level.components.items.types

import android.widget.ImageView
import ca.thenightcrew.supervinebros.R
import ca.thenightcrew.supervinebros.animation.AnimationEngine
import ca.thenightcrew.supervinebros.game_engine.level.SlideLevelManager
import ca.thenightcrew.supervinebros.game_engine.level.components.items.LevelItem
import ca.thenightcrew.supervinebros.game_engine.sprite.Sprite
import ca.thenightcrew.supervinebros.game_engine.status.ScoreKeeper


/**
 * A Big coin Item is like a [CoinItem] but is worth 10 times the amount
 */
class BigCoinItem : LevelItemAnimator(), LevelItem {
    private var useFinalResource = false
    /** See [LevelItem.getNextResourceID]  */
    /** The drawable resources used when this coin appears  */
    override val resourceIDs = intArrayOf(
        R.drawable.slide_coin_00000,
        R.drawable.slide_coin_00001,
        R.drawable.slide_coin_00002,
        R.drawable.slide_coin_00003
    )
        get() = if (useFinalResource) finalResourceIDs else field

    /** The drawable resources used when this coin disappears  */
    private val finalResourceIDs = intArrayOf(
        R.drawable.slide_collect_00000,
        R.drawable.slide_collect_00002,
        R.drawable.slide_empty_0000
    )

    /** See [LevelItem.interact]  */
    override fun interact(
        sprite: Sprite,
        score: ScoreKeeper,
        levelManager: SlideLevelManager,
        itemImageView: ImageView,
        animationEngine: AnimationEngine
    ){
        useFinalResource = true
        setFinalAnimation()
        score.increaseBy(10)
    }

    /** See [LevelItem.getSize]  */
    override val size: Int
        get() = 70
}
