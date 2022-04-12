package ca.thenightcrew.supervinebros.game_engine.sprite

import android.view.View
import android.widget.ImageView
import ca.thenightcrew.supervinebros.R


/**
 * A Binder class that binds all the sprite xml elements to given by the SpriteResource class which
 * holds resource information. It contains a List for walking sprites and sprites on vine for easy
 * animations of those groups of ImageViews
 */
open class SpriteBinder(view: View, resource: SpriteResource) {
    val animationManager: SpriteAnimationManager = SpriteAnimationManager()
    var positionManager: SpritePositionManager = SpritePositionManager()
    val hurt: ImageView
    var vineComponents: List<ImageView>
    var walkComponents: List<ImageView>
    val hands: ImageView
    val blink: ImageView
    val legs: ImageView
    val body: ImageView
    val onTopOfVine: ImageView
    val walk1: ImageView
    val walk2: ImageView
    val walk3: ImageView
    val peace: ImageView

    init {
        hands = view.findViewById(R.id.sprite_hands)
        blink = view.findViewById(R.id.sprite_eyes_blink)
        legs = view.findViewById(R.id.sprite_legs)
        body = view.findViewById(R.id.sprite_body)
        onTopOfVine = view.findViewById(R.id.sprite_on_top_vine)
        walk1 = view.findViewById(R.id.sprite_walk_1)
        walk2 = view.findViewById(R.id.sprite_walk_2)
        walk3 = view.findViewById(R.id.sprite_walk_3)
        peace = view.findViewById(R.id.sprite_peace)
        hurt = view.findViewById(R.id.sprite_hurt)
        body.setImageResource(resource.body)
        legs.setImageResource(resource.legs)
        blink.setImageResource(resource.eyes)
        hands.setImageResource(resource.hands)
        onTopOfVine.setImageResource(resource.vineTop)
        walk1.setImageResource(resource.walk1)
        walk2.setImageResource(resource.walk2)
        walk3.setImageResource(resource.walk3)
        peace.setImageResource(resource.peace)
        hurt.setImageResource(resource.hurt)

        vineComponents = mutableListOf(body, hands, legs, blink, hurt)
        walkComponents = mutableListOf(walk1, walk2, walk3)

        positionManager.positionSprite("LEFT", vineComponents)
    }
}