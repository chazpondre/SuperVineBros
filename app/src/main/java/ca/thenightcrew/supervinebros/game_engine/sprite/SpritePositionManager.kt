package ca.thenightcrew.supervinebros.game_engine.sprite

import android.widget.ImageView
import ca.thenightcrew.supervinebros.game_engine.Utils.Measurement.pixelToDP

/**
 * Used to keep track of the the position of a sprite and how to scale and translate into that
 * position
 */
class SpritePositionManager {
  private var position = ""

  /** Positions a sprite. If a sprite is already in that position this method return false else true
   * @param position The position that the sprite should move into
   * @param spriteComponents The components that make up the sprite on a vine
   * @return
   */
  fun positionSprite(position: String, spriteComponents: List<ImageView>): Boolean {
    if (this.position.equals(position, ignoreCase = true)) return false
    val translateX: Float = if (position == "LEFT") pixelToDP(-25) else pixelToDP(25)
    val scaleX = if (position == "LEFT") -2f else 2f
    for (spriteComponent in spriteComponents) {
      spriteComponent.translationX = translateX
      spriteComponent.scaleX = scaleX
    }
    this.position = position
    return true
  }

  /** @return The current position the sprite is in
   */
  fun getPosition(): String {
    return if (position.isEmpty()) "LEFT" else position
  }
}
