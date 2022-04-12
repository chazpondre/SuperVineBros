package ca.thenightcrew.supervinebros.game_engine.sprite

import android.view.View

/**
 * A factory class used for creating sprites. Even though their is only one sprite it allows the
 * expansion of many more easily
 */
object SpriteFactory {
    internal fun mario(view: View) = Sprite(view, Mario())
//    fun create(spriteName: String, view: View?): Sprite? {
//        var spriteResource: SpriteResource? = null
//        if (spriteName.equals("mario", ignoreCase = true)) {
//            spriteResource = Mario()
//        }
//        return spriteResource?.let { Sprite(view, it) }
//    }
}
